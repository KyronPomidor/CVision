package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.JobPostingSkill;
import com.pbl.back.dto.jobposting.PostingSkillResponse;
import com.pbl.back.mapper.JobPostingSkillMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobPostingSkillMapperImpl implements JobPostingSkillMapper {

    @Override
    public List<PostingSkillResponse> toSkillResponse(List<JobPostingSkill> mappings) {
        return mappings.stream()
                .map(mapping -> PostingSkillResponse.builder()
                        .id(mapping.getSkill().getId())
                        .name(mapping.getSkill().getName())
                        .yearsOfExperience(mapping.getRequiredYears())
                        .build())
                .toList();
    }
}
