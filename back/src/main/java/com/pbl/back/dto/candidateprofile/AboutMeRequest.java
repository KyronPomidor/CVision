package com.pbl.back.dto.candidateprofile;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AboutMeRequest {

    @Size(max = 5000, message = "must be at most 5000 characters")
    private String description;
}
