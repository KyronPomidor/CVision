package com.pbl.back.dto.skill;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SkillResponse {

    private Long id;
    private String name;
}
