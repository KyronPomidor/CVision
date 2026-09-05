package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.skill.SkillRequest;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.mapper.SkillMapper;
import org.springframework.stereotype.Component;

@Component
public class SkillMapperImpl implements SkillMapper {

    @Override
    public Skill toEntity(SkillRequest request) {
        return Skill.builder()
                .name(request.getName())
                .build();
    }

    @Override
    public SkillResponse toResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
