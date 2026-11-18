package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CV;
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
                        .yearsOfExperience(null)
                        .build());
            }

            if (profile != null
                    && !profileSkillRepository.existsByProfileIdAndSkillId(profile.getId(), skill.getId())) {
                profileSkillRepository.save(ProfileSkill.builder()
                        .profile(profile)
                        .skill(skill)
                        .level(DEFAULT_AI_SKILL_LEVEL)
                        .yearsOfExperience(null)
                        .build());
            }
        }

        return savedCv;
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
