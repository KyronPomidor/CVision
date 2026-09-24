package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobPostingDetails;
import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.dto.jobposting.JobPostingDetailsResponse;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.mapper.CompanyMapper;
import com.pbl.back.mapper.JobPostingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JobPostingMapperImpl implements JobPostingMapper {

    private final CompanyMapper companyMapper;

    @Override
    public JobPosting toEntity(JobPostingRequest request) {

        JobPostingDetails details = JobPostingDetails.builder()
                .jobType(request.getDetails().getJobType())
                .workSetting(request.getDetails().getWorkSetting())
                .location(request.getDetails().getLocation())
                .salary(request.getDetails().getSalary())
                .schedule(request.getDetails().getSchedule())
                .experience(request.getDetails().getExperience())
                .education(request.getDetails().getEducation())
                .contactEmail(request.getDetails().getContactEmail())
                .build();

        return JobPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .details(details)
                .createdAt(LocalDateTime.now())
                .status(JobStatus.OPEN)
                .build();
    }

    @Override
    public JobPostingResponse toResponse(
            JobPosting posting,
            List<SkillResponse> skills
    ) {

        JobPostingDetailsResponse details =
                JobPostingDetailsResponse.builder()
                        .jobType(posting.getDetails().getJobType())
                        .workSetting(posting.getDetails().getWorkSetting())
                        .location(posting.getDetails().getLocation())
                        .salary(posting.getDetails().getSalary())
                        .schedule(posting.getDetails().getSchedule())
                        .experience(posting.getDetails().getExperience())
                        .education(posting.getDetails().getEducation())
                        .contactEmail(posting.getDetails().getContactEmail())
                        .build();

        return JobPostingResponse.builder()
                .id(posting.getId())
                .company(companyMapper.toResponse(posting.getCompany()))
                .title(posting.getTitle())
                .description(posting.getDescription())
                .details(details)
                .createdAt(posting.getCreatedAt())
                .status(posting.getStatus())
                .skills(skills)
                .build();
    }
}