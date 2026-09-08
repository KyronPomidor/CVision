package com.pbl.back.dto.candidateprofile;

import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CandidateProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String location;
    private String education;
    private String experience;
    private String description;
    private List<ProfileSkillResponse> skills;
}
