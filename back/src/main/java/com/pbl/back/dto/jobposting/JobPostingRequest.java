package com.pbl.back.dto.jobposting;

import com.pbl.back.domain.enums.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class JobPostingRequest {
    @NotBlank
    private String title;
    private String description;
    private String location;
    private EmploymentType employmentType;
    @PositiveOrZero
    private BigDecimal salary;
    private List<Long> skillIds;
}
