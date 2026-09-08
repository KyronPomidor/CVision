package com.pbl.back.dto.cvskill;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVSkillRequest {

    @NotNull
    private Long skillId;

    private Double confidenceScore;

    private Integer yearsOfExperience;
}