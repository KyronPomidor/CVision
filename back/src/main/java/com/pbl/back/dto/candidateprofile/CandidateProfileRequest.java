package com.pbl.back.dto.candidateprofile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateProfileRequest {

    @NotBlank
    private String name;
    private String location;
    private String education;
    private String experience;
    private String description;
}
