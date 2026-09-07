package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobRecommendation;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.mapper.JobRecommendationMapper;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.JobRecommendationRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.JobRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobRecommendationServiceImpl implements JobRecommendationService {

    private final JobRecommendationRepository repository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobRecommendationMapper mapper;

    @Override
    public JobRecommendationResponse create(Long userId, JobRecommendationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow();

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow();

        JobRecommendation recommendation = mapper.toEntity(request);

        recommendation.setUser(user);
        recommendation.setJobPosting(jobPosting);
        recommendation.setCreatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(recommendation));
    }

    @Override
    public JobRecommendationResponse getById(Long id) {
        JobRecommendation recommendation = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(recommendation);
    }

    @Override
    public List<JobRecommendationResponse> getByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<JobRecommendationResponse> getByJobPostingId(Long jobPostingId) {
        return repository.findByJobPostingId(jobPostingId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public JobRecommendationResponse update(Long id, JobRecommendationRequest request) {
        JobRecommendation recommendation = repository.findById(id)
                .orElseThrow();

        recommendation.setMatchScore(request.getMatchScore());
        recommendation.setExplanation(request.getExplanation());

        return mapper.toResponse(repository.save(recommendation));
    }

    @Override
    public void delete(Long id) {
        JobRecommendation recommendation = repository.findById(id)
                        .orElseThrow();

        repository.delete(recommendation);
    }
}