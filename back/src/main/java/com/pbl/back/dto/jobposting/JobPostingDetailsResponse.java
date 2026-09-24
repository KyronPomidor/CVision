package com.pbl.back.dto.jobposting;

import com.pbl.back.domain.enums.JobType;
import com.pbl.back.domain.enums.Schedule;
import com.pbl.back.domain.enums.WorkSetting;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class JobPostingDetailsResponse {

    private JobType jobType;

    private WorkSetting workSetting;

    private String location;

    private BigDecimal salary;

    private Schedule schedule;

    private Integer experience;

    private String education;

    private String contactEmail;
}