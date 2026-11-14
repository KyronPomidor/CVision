package com.pbl.back.dto.jobrecommendation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRecommendationRequest {

    @NotNull(message = "is required")
    private Long jobPostingId;

    @DecimalMin(value = "0.0", message = "must be at least 0.0")
    @DecimalMax(value = "100.0", message = "must be at most 100.0")
    private Double matchScore;

    @Size(max = 2000, message = "must be at most 2000 characters")
    private String explanation;
}