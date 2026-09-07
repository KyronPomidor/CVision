package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.JobRecommendation;
import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.mapper.JobRecommendationMapper;
import org.springframework.stereotype.Component;

@Component
public class JobRecommendationMapperImpl implements JobRecommendationMapper {

    public JobRecommendation toEntity(JobRecommendationRequest request) {
        return JobRecommendation.builder()
                .matchScore(request.getMatchScore())
                .explanation(request.getExplanation())
                .build();
    }

    public JobRecommendationResponse toResponse(JobRecommendation recommendation) {

        return JobRecommendationResponse.builder()
                .id(recommendation.getId())
                .userId(recommendation.getUser().getId())
                .jobPostingId(recommendation.getJobPosting().getId())
                .matchScore(recommendation.getMatchScore())
                .explanation(recommendation.getExplanation())
                .createdAt(recommendation.getCreatedAt())
                .build();
    }
}