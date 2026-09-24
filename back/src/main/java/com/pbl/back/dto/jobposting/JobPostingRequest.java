package com.pbl.back.dto.jobposting;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobPostingRequest {

    @NotBlank
    private String title;

    private String description;

    @Valid
    private JobPostingDetailsRequest details;

    private List<Long> skillIds;
}