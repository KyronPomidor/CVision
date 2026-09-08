package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileRepository repository;
    private final CandidateProfileMapper mapper;
    private final UserRepository userRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final ProfileSkillMapper profileSkillMapper;

    // TODO: get user without client id
    public CandidateProfileResponse create(Long userId, CandidateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        CandidateProfile profile = mapper.toEntity(request);

        profile.setUser(user);

        CandidateProfile savedProfile = repository.save(profile);

        return mapper.toResponse(savedProfile, List.of());
    }

    @Override
    public CandidateProfileResponse getByUserId(Long userId) {

        CandidateProfile profile = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("" +
                        "CandidateProfile for user with id '" + userId + "' not found."));

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(profile, skills);
    }

    @Override
    public CandidateProfileResponse getById(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(profile, skills);
    }

    @Override
    public CandidateProfileResponse update(Long id, CandidateProfileRequest request) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));

        profile.setName(request.getName());
        profile.setLocation(request.getLocation());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setDescription(request.getDescription());

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(repository.save(profile), skills);
    }

    @Override
    public void delete(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));

        repository.delete(profile);
    }


}