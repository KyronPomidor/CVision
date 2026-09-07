package com.pbl.back.dto.cvskill;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CVSkillResponse {

    private Long id;
    private Long cvId;
    private Long skillId;
    private String skillName;
    private Double confidenceScore;
    private Integer yearsOfExperience;
}