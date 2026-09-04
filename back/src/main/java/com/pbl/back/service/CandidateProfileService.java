package com.pbl.back.service;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.dto.CandidateProfileRequest;
import com.pbl.back.dto.CandidateProfileResponse;

public interface CandidateProfileService {
    public CandidateProfileResponse create(CandidateProfileRequest request);
}
