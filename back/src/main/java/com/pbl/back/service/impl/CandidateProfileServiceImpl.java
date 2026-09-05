package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileRepository repository;
    private final CandidateProfileMapper mapper;
    private final UserRepository userRepository;

    // TODO: get user without client id
    public CandidateProfileResponse create(Long userId, CandidateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow();

        CandidateProfile profile = mapper.toEntity(request);

        profile.setUser(user);

        CandidateProfile savedProfile = repository.save(profile);

        return mapper.toResponse(savedProfile);
    }

    @Override
    public CandidateProfileResponse getByUserId(Long userId) {
        CandidateProfile profile = repository.findByUserId(userId)
                .orElseThrow();

        return mapper.toResponse(profile);
    }

    @Override
    public CandidateProfileResponse getById(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(profile);
    }

    @Override
    public CandidateProfileResponse update(Long id, CandidateProfileRequest request) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow();

        profile.setName(request.getName());
        profile.setLocation(request.getLocation());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(profile));
    }

    @Override
    public void delete(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow();

        repository.delete(profile);
    }


}