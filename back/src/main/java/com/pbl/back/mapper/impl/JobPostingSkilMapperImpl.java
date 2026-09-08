package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.JobPostingSkill;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.mapper.JobPostingSkillMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobPostingSkilMapperImpl implements JobPostingSkillMapper {
    @Override
    public List<SkillResponse> toSkillResponse(List<JobPostingSkill> mappings) {
        return mappings.stream()
                .map(mapping -> SkillResponse.builder()
                        .id(mapping.getSkill().getId())
                        .name(mapping.getSkill().getName())
                        .build())
                .toList();
    }
}
