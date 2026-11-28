package com.pbl.back.mapper;

import com.pbl.back.domain.entity.JobPostingSkill;
import com.pbl.back.dto.jobposting.PostingSkillResponse;

import java.util.List;

public interface JobPostingSkillMapper {
    List<PostingSkillResponse> toSkillResponse(List<JobPostingSkill> mappings);
}
