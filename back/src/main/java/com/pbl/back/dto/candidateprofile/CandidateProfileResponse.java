package com.pbl.back.dto.candidateprofile;

import lombok.Builder;
import lombok.Getter;

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
}
