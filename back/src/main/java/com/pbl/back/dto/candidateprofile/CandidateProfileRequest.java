package com.pbl.back.dto.candidateprofile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateProfileRequest {

    @NotBlank(message = "is required")
    @Size(min = 3, max = 100, message = "must be 3-100 characters")
    private String name;
    private String phone;

    @Size(max = 255, message = "must be at most 255 characters")
    private String location;

    @Size(max = 255, message = "must be at most 255 characters")
    private String education;

    @Size(max = 5000, message = "must be at most 5000 characters")
    private String experience;

    @Size(max = 5000, message = "must be at most 5000 characters")
    private String description;
}
