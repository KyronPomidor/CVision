package com.pbl.back.service;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;

public interface CandidateProfileService {
    public CandidateProfileResponse create(CandidateProfileRequest request);

//    public CandidateProfileResponse getById(Long id);

    public CandidateProfileResponse getByUserId(Long userId);

    public CandidateProfileResponse update(Long userId, CandidateProfileRequest request);

    public void delete(Long userId);
}
