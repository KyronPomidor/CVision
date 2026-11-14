package com.pbl.back.dto.cvskill;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVSkillRequest {

    @NotNull(message = "is required")
    private Long skillId;

    @DecimalMin(value = "0.0", message = "must be at least 0.0")
    @DecimalMax(value = "1.0", message = "must be at most 1.0")
    private Double confidenceScore;

    @PositiveOrZero(message = "must be greater than or equal to 0")
    @Max(value = 60, message = "must be at most 60")
    private Integer yearsOfExperience;
}