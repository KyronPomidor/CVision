package com.pbl.back.mapper;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.skill.SkillResponse;

import java.util.List;

public interface JobPostingMapper {
    JobPosting toEntity(JobPostingRequest request);

    JobPostingResponse toResponse(JobPosting posting, List<SkillResponse> skills);
}
