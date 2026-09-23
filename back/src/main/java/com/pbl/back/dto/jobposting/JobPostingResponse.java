package com.pbl.back.dto.jobposting;

import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.dto.skill.SkillResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class JobPostingResponse {

    private Long id;

    private CompanyResponse company;

    private String title;

    private String description;

    private JobPostingDetailsResponse details;

    private LocalDateTime createdAt;

    private JobStatus status;

    private List<SkillResponse> skills;
}