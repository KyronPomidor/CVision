package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.ProfileSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.ProfileSkillService;
import com.pbl.back.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileSkillServiceImpl implements ProfileSkillService {

    private final ProfileSkillRepository repository;
    private final SkillRepository skillRepository;
    private final ProfileSkillMapper mapper;
    private final CandidateProfileRepository profileRepository;
    private final CurrentUserService currentUserService;

    @Override
    public ProfileSkillResponse addSkill(ProfileSkillRequest request) {
        CandidateProfile profile = getCurrentProfile();

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill with id '" + request.getSkillId() + "' not found."));

        ProfileSkill profileSkill = ProfileSkill.builder()
                .profile(profile)
                .skill(skill)
                .level(request.getLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .build();

        return mapper.toResponse(repository.save(profileSkill));
    }

    @Override
    public List<ProfileSkillResponse> getSkills() {
        return repository.findByProfileId(getCurrentProfile().getId())
                .stream()
                .map(p -> mapper.toResponse(p))
                .toList();
    }

    @Override
    public ProfileSkillResponse getById(Long id) {
        ProfileSkill profileSkill = getOwnedSkill(id);

        return mapper.toResponse(profileSkill);
    }

    @Override
    public ProfileSkillResponse update(Long id, ProfileSkillRequest request) {
        ProfileSkill profileSkill = getOwnedSkill(id);

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill with id '" + request.getSkillId() + "' not found."));

        profileSkill.setSkill(skill);
        profileSkill.setLevel(request.getLevel());
        profileSkill.setYearsOfExperience(request.getYearsOfExperience());

        return mapper.toResponse(repository.save(profileSkill));
    }

    @Override
    public void delete(Long id) {
        ProfileSkill profileSkill = getOwnedSkill(id);

        repository.delete(profileSkill);
    }

    private CandidateProfile getCurrentProfile() {
        return profileRepository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your profile first."));
    }

    private ProfileSkill getOwnedSkill(Long id) {
        ProfileSkill skill = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProfileSkill with id '" + id + "' not found."));
        if (!skill.getProfile().getUser().getId().equals(currentUserService.getId())) {
            throw new ResourceNotFoundException("ProfileSkill with id '" + id + "' not found.");
        }
        return skill;
    }
}