package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.mapper.JobPostingMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JobPostingMapperImpl implements JobPostingMapper {
    @Override
    public JobPosting toEntity(JobPostingRequest request) {
        return JobPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .employmentType(request.getEmploymentType())
                .salary(request.getSalary())
                .createdAt(LocalDateTime.now())
                .status(JobStatus.OPEN)
                .build();
    }

    @Override
    public JobPostingResponse toResponse(JobPosting posting, List<SkillResponse> skills) {
        return JobPostingResponse.builder()
                .id(posting.getId())
                .companyId(posting.getCompany().getId())
                .title(posting.getTitle())
                .description(posting.getDescription())
                .location(posting.getLocation())
                .employmentType(posting.getEmploymentType())
                .salary(posting.getSalary())
                .createdAt(LocalDateTime.now())
                .status(posting.getStatus())
                .skills(skills)
                .build();
    }
}
