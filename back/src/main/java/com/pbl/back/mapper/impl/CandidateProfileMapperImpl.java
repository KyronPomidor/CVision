package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.mapper.CandidateProfileMapper;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public CandidateProfileResponse toResponse(CandidateProfile profile, List<ProfileSkillResponse> skills) {

        return CandidateProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .name(profile.getName())
                .location(profile.getLocation())
                .education(profile.getEducation())
                .experience(profile.getExperience())
                .description(profile.getDescription())
                .skills(skills)
                .build();
    }
}
