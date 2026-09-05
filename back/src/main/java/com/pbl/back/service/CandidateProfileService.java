package com.pbl.back.service;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;

public interface CandidateProfileService {
    public CandidateProfileResponse create(Long userId, CandidateProfileRequest request);

    public CandidateProfileResponse getByUserId(Long userId);

    public CandidateProfileResponse getById(Long id);

    public CandidateProfileResponse update(Long id, CandidateProfileRequest request);

    public void delete(Long id);
}
