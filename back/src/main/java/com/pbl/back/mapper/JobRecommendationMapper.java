package com.pbl.back.mapper;

import com.pbl.back.domain.entity.JobRecommendation;
import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;

public interface JobRecommendationMapper {
    JobRecommendation toEntity(JobRecommendationRequest request);

    JobRecommendationResponse toResponse(JobRecommendation recommendation);
}
