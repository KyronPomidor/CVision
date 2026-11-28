package com.pbl.back.dto.jobposting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingSkillResponse {
    private Long id;
    private String name;
    private Integer yearsOfExperience;
}
