package com.pbl.back.mapper;

import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.skill.SkillRequest;
import com.pbl.back.dto.skill.SkillResponse;

public interface SkillMapper {
    Skill toEntity(SkillRequest request);

    SkillResponse toResponse(Skill skill);
}
