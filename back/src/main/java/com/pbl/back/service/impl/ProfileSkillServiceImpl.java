package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.ProfileSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.ProfileSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileSkillServiceImpl implements ProfileSkillService {

    private final ProfileSkillRepository repository;
    private final CandidateProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final ProfileSkillMapper mapper;

    @Override
    public ProfileSkillResponse addSkill(Long profileId, ProfileSkillRequest request) {
        CandidateProfile profile = profileRepository.findById(profileId)
                .orElseThrow();

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow();

        ProfileSkill profileSkill = ProfileSkill.builder()
                .profile(profile)
                .skill(skill)
                .level(request.getLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .build();

        return mapper.toResponse(repository.save(profileSkill));
    }

    @Override
    public List<ProfileSkillResponse> getSkills(Long profileId) {
        return repository.findByProfileId(profileId)
                .stream()
                .map(p -> mapper.toResponse(p))
                .toList();
    }

    @Override
    public ProfileSkillResponse getById(Long id) {
        ProfileSkill profileSkill = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(profileSkill);
    }

    @Override
    public ProfileSkillResponse update(Long id, ProfileSkillRequest request) {
        ProfileSkill profileSkill = repository.findById(id)
                .orElseThrow();

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow();

        profileSkill.setSkill(skill);
        profileSkill.setLevel(request.getLevel());
        profileSkill.setYearsOfExperience(request.getYearsOfExperience());

        return mapper.toResponse(repository.save(profileSkill));
    }

    @Override
    public void delete(Long id) {
        ProfileSkill profileSkill = repository.findById(id)
                .orElseThrow();

        repository.delete(profileSkill);
    }
}