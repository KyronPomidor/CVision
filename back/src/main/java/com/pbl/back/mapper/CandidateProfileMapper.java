package com.pbl.back.mapper;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;

public interface CandidateProfileMapper {
    public CandidateProfile toEntity(CandidateProfileRequest request);

    public CandidateProfileResponse toResponse(CandidateProfile profile);
}
