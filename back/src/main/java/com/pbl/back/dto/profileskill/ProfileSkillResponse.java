package com.pbl.back.dto.profileskill;

import com.pbl.back.domain.enums.SkillLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileSkillResponse {

    private Long id;
    private Long skillId;
    private String skillName;
    private SkillLevel level;
    private Integer yearsOfExperience;
}