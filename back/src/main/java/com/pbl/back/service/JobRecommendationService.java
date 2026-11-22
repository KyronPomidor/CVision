package com.pbl.back.service;

import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;

import java.util.List;

public interface JobRecommendationService {
    JobRecommendationResponse create(JobRecommendationRequest request);

    JobRecommendationResponse getById(Long id);

    List<JobRecommendationResponse> getMine();

    List<JobRecommendationResponse> getByJobPostingId(Long jobPostingId);

    JobRecommendationResponse update(Long id, JobRecommendationRequest request);

    void delete(Long id);
}
