package com.pbl.back.dto.jobposting;

import com.pbl.back.domain.enums.JobType;
import com.pbl.back.domain.enums.Schedule;
import com.pbl.back.domain.enums.WorkSetting;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JobPostingDetailsRequest {

    private JobType jobType;

    private WorkSetting workSetting;

    private String location;

    @PositiveOrZero
    private BigDecimal salary;

    private Schedule schedule;

    @PositiveOrZero
    private Integer experience;

    private String education;

    private String contactEmail;
}