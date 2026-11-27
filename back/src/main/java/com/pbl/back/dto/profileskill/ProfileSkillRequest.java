package com.pbl.back.dto.profileskill;

import jakarta.validation.constraints.NotNull;
import com.pbl.back.domain.enums.SkillLevel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileSkillRequest {

    @NotNull(message = "is required")
    private Long skillId;

    @NotNull(message = "is required")
    private SkillLevel level;

    @PositiveOrZero(message = "must be greater than or equal to 0")
    @Max(value = 60, message = "must be at most 60")
    private Integer yearsOfExperience;
}