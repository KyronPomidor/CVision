package com.pbl.back.dto.application;

import com.pbl.back.domain.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequest {
    @NotNull
    private Long jobPostingId;

    @NotNull
    private Long cvId;

    @NotNull
    private ApplicationStatus status;
}
