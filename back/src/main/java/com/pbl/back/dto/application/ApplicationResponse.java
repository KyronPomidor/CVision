package com.pbl.back.dto.application;

import com.pbl.back.domain.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApplicationResponse {

    private Long id;
    private Long userId;
    private Long jobPostingId;
    private Long cvId;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}
