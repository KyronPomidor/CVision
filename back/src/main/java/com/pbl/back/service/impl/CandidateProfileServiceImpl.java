package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.service.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileRepository repository;
    private final CandidateProfileMapper mapper;

    public CandidateProfileResponse create(CandidateProfileRequest request) {

        CandidateProfile profile = mapper.toEntity(request);

        CandidateProfile savedProfile = repository.save(profile);

        return mapper.toResponse(savedProfile);
    }
}