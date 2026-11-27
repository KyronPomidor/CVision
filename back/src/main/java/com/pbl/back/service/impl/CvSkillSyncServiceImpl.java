package com.pbl.back.service.impl;

import java.util.TreeMap;
import java.util.Objects;
import com.pbl.back.dto.skill.ExtractedSkill;
import com.pbl.back.domain.entity.CV;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl.back.domain.entity.CVSkill;
import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.ProfileSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.domain.enums.SkillLevel;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.CVSkillRepository;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.CvSkillSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CvSkillSyncServiceImpl implements CvSkillSyncService {

    private static final SkillLevel DEFAULT_AI_SKILL_LEVEL = SkillLevel.BEGINNER;
    private static final int MAX_SKILL_NAMES = 100;
    private static final int MAX_SKILL_NAME_LENGTH = 100;

    private final CVRepository cvRepository;
    private final SkillRepository skillRepository;
    private final CVSkillRepository cvSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CV saveCvWithSkills(CV cv, List<String> skillNames) {
        CV savedCv = cvRepository.save(cv);
        List<String> normalizedNames = normalizeSkillNames(skillNames);
        if (normalizedNames.isEmpty()) {
            return savedCv;
        }

        CandidateProfile profile = candidateProfileRepository.findByUserId(savedCv.getUser().getId())
                .orElse(null);
        if (profile == null) {
            log.info("No candidate profile found for user {}; skipping profile skill synchronization",
                    savedCv.getUser().getId());
        }

        for (String name : normalizedNames) {
            Skill skill = skillRepository.findFirstByNameIgnoreCase(name)
                    .orElseGet(() -> skillRepository.save(Skill.builder().name(name).build()));

            if (!cvSkillRepository.existsByCvIdAndSkillId(savedCv.getId(), skill.getId())) {
                cvSkillRepository.save(CVSkill.builder()
                        .cv(savedCv)
                        .skill(skill)
                        .confidenceScore(null)
                        .build());
            }

            if (profile != null
                    && !profileSkillRepository.existsByProfileIdAndSkillId(profile.getId(), skill.getId())) {
                profileSkillRepository.save(ProfileSkill.builder()
                        .profile(profile)
                        .skill(skill)
                        .level(DEFAULT_AI_SKILL_LEVEL)
                        .build());
            }
        }

        return savedCv;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CV replaceCvWithSkills(CV cv, List<String> skillNames) {
        List<String> names = normalizeSkillNames(skillNames);
        Set<String> desiredNames = new HashSet<>();
        names.forEach(name -> desiredNames.add(name.toLowerCase(Locale.ROOT)));

        List<CVSkill> previousSkills = cv.getId() == null
                ? List.of() : cvSkillRepository.findByCvId(cv.getId());
        List<CVSkill> removedSkills = previousSkills.stream()
                .filter(link -> !desiredNames.contains(link.getSkill().getName().toLowerCase(Locale.ROOT)))
                .toList();
        Set<Long> removedIds = new HashSet<>();
        removedSkills.forEach(link -> removedIds.add(link.getSkill().getId()));
        cvSkillRepository.deleteAll(removedSkills);

        candidateProfileRepository.findByUserId(cv.getUser().getId()).ifPresent(profile -> {
            List<ProfileSkill> removedProfileSkills = profileSkillRepository.findByProfileId(profile.getId())
                    .stream()
                    .filter(link -> removedIds.contains(link.getSkill().getId()))
                    .toList();
            profileSkillRepository.deleteAll(removedProfileSkills);
        });

        try {
            cv.setSkills(objectMapper.writeValueAsString(names));
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Could not serialize CV skills", ex);
        }
        return saveCvWithSkills(cv, names);
    }

    @Override
    @Transactional
    public CV replaceExtractedSkills(CV cv, List<ExtractedSkill> skills) {
        var years = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        for (ExtractedSkill skill : skills) {
            if (skill != null && skill.name() != null) {
                String name = skill.name().trim().replaceAll("(?U)\\s+", " ").trim();
                years.putIfAbsent(name, null);
                if (skill.yearsOfExperience() != null) {
                    years.merge(name, skill.yearsOfExperience(), Math::max);
                }
            }
        }
        CV saved = replaceCvWithSkills(cv, skills.stream().filter(Objects::nonNull)
                .map(ExtractedSkill::name).toList());
        for (CVSkill link : cvSkillRepository.findByCvId(saved.getId())) {
            link.setYearsOfExperience(years.get(link.getSkill().getName()));
        }
        candidateProfileRepository.findByUserId(saved.getUser().getId()).ifPresent(profile -> {
            for (ProfileSkill link : profileSkillRepository.findByProfileId(profile.getId())) {
                Integer value = years.get(link.getSkill().getName());
                if (years.containsKey(link.getSkill().getName())) link.setYearsOfExperience(value);
            }
        });
        return saved;
    }

    private List<String> normalizeSkillNames(List<String> skillNames) {
        List<String> normalizedNames = new ArrayList<>();
        Set<String> seenNames = new HashSet<>();

        for (String skillName : skillNames) {
            if (skillName == null) {
                continue;
            }

            String normalizedName = skillName.trim().replaceAll("(?U)\\s+", " ").trim();
            if (normalizedName.isBlank() || normalizedName.length() > MAX_SKILL_NAME_LENGTH) {
                continue;
            }

            if (seenNames.add(normalizedName.toLowerCase(Locale.ROOT))) {
                normalizedNames.add(normalizedName);
                if (normalizedNames.size() == MAX_SKILL_NAMES) {
                    break;
                }
            }
        }

        return normalizedNames;
    }
}
