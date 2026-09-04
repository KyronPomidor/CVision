package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.mapper.CandidateProfileMapper;
import org.springframework.stereotype.Component;

@Component
public class CandidateProfileMapperImpl implements CandidateProfileMapper {

    public CandidateProfile toEntity(CandidateProfileRequest request) {

        return CandidateProfile.builder()
                .name(request.getName())
                .location(request.getLocation())
                .education(request.getEducation())
                .experience(request.getExperience())
                .description(request.getDescription())
                .build();
    }

    public CandidateProfileResponse toResponse(CandidateProfile profile) {

        return CandidateProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .location(profile.getLocation())
                .education(profile.getEducation())
                .experience(profile.getExperience())
                .description(profile.getDescription())
                .build();
    }
}
