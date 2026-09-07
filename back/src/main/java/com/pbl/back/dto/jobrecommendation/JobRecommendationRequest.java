package com.pbl.back.dto.jobrecommendation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRecommendationRequest {

    @NotNull
    private Long jobPostingId;

    private Double matchScore;

    private String explanation;
}