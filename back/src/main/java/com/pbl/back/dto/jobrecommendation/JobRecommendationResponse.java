package com.pbl.back.dto.jobrecommendation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class JobRecommendationResponse {

    private Long id;
    private Long userId;
    private Long jobPostingId;
    private Double matchScore;
    private String explanation;
    private LocalDateTime createdAt;
}