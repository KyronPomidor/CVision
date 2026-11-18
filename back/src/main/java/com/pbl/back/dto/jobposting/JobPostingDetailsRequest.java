package com.pbl.back.dto.jobposting;

import com.pbl.back.domain.enums.JobType;
import com.pbl.back.domain.enums.Schedule;
import com.pbl.back.domain.enums.WorkSetting;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JobPostingDetailsRequest {

    private JobType jobType;

    private WorkSetting workSetting;

    @Size(max = 255, message = "must be at most 255 characters")
    private String location;

    @PositiveOrZero(message = "must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "must have at most 10 integer and 2 decimal digits")
    private BigDecimal salary;

    private Schedule schedule;

    @PositiveOrZero(message = "must be greater than or equal to 0")
    @Max(value = 60, message = "must be at most 60")
    private Integer experience;

    @Size(max = 255, message = "must be at most 255 characters")
    private String education;

    @Size(max = 254, message = "must be at most 254 characters")
    @Email(regexp = "^$|^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$",
            message = "must be a valid email address")
    private String contactEmail;
}