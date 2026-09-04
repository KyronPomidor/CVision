package com.pbl.back.service;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;

public interface CandidateProfileService {
    public CandidateProfileResponse create(CandidateProfileRequest request);
}
