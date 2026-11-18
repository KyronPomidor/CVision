package com.pbl.back.dto.candidateprofile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrentCandidateProfileResponse {

    private String name;
    private String email;
    private String phone;
    private String location;
    private String description;
}
