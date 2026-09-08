package com.pbl.back.mapper;

import com.pbl.back.domain.entity.JobPostingSkill;
import com.pbl.back.dto.skill.SkillResponse;

import java.util.List;

public interface JobPostingSkillMapper {
    List<SkillResponse> toSkillResponse(List<JobPostingSkill> mappings);
}
