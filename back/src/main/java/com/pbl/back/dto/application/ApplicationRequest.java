package com.pbl.back.dto.application;

import com.pbl.back.domain.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequest {
    @NotNull(message = "is required")
    private Long jobPostingId;

    @NotNull(message = "is required")
    private Long cvId;

    @NotNull(message = "is required")
    private ApplicationStatus status;
}
