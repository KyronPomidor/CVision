package com.pbl.back.dto.jobposting;

import com.pbl.back.domain.enums.EmploymentType;
import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.dto.skill.SkillResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class JobPostingResponse {
    private Long id;
    private Long companyId;
    private String title;
    private String description;
    private String location;
    private EmploymentType employmentType;
    private BigDecimal salary;
    private LocalDateTime createdAt;
    private JobStatus status;
    private List<SkillResponse> skills;
}
