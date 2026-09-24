package com.pbl.back.domain.entity;

import com.pbl.back.domain.enums.JobType;
import com.pbl.back.domain.enums.Schedule;
import com.pbl.back.domain.enums.WorkSetting;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingDetails {

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private WorkSetting workSetting;

    private String location;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Schedule schedule;

    private Integer experience;

    private String education;

    private String contactEmail;
}