package com.pbl.back.dto.profileskill;

import com.pbl.back.domain.enums.SkillLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileSkillRequest {

    @NotNull
    private Long skillId;

    @NotNull
    private SkillLevel level;

    private Integer yearsOfExperience;
}