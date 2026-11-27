package com.pbl.back.dto.jobposting;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Getter
@Setter
public class JobPostingRequest {

    @NotBlank(message = "is required")
    @Size(min = 3, max = 150, message = "must be 3-150 characters")
    private String title;

    @Size(max = 10000, message = "must be at most 10000 characters")
    private String description;

    @NotNull(message = "is required")
    @Valid
    private JobPostingDetailsRequest details;

    @Size(max = 50, message = "must contain at most 50 skills")
    @Setter(AccessLevel.NONE)
    private List<@NotNull(message = "must not be null") @Positive(message = "must be greater than 0") Long>
            skillIds = new ArrayList<>();

    @Size(max = 50)
    private Map<@Positive Long, @NotNull @Min(0) @Max(60) Integer> requiredYearsBySkill;

    public void setSkillIds(List<Long> skillIds) {
        this.skillIds = skillIds == null ? new ArrayList<>() : skillIds;
    }
}