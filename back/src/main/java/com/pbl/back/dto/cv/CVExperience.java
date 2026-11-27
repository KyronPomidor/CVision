package com.pbl.back.dto.cv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CVExperience(
        Long id,
        @NotBlank @Size(max = 255) String title,
        @Size(max = 255) String company,
        @Size(max = 255) String location,
        @NotNull LocalDate startDate,
        LocalDate endDate,
        @Size(max = 5000) String description
) {
    @JsonIgnore
    @AssertTrue(message = "end date must be on or after start date")
    public boolean isPeriodValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}
