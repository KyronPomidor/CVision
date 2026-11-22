package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobRecommendation;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.JobRecommendationMapper;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.JobRecommendationRepository;
import com.pbl.back.service.JobRecommendationService;
import com.pbl.back.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobRecommendationServiceImpl implements JobRecommendationService {

    private final JobRecommendationRepository repository;
    private final JobPostingRepository jobPostingRepository;
    private final JobRecommendationMapper mapper;
    private final CurrentUserService currentUserService;

    @Override
    public JobRecommendationResponse create(JobRecommendationRequest request) {
        User user = currentUserService.get();

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobPosting with id '" + request.getJobPostingId() + "' not found."));

        JobRecommendation recommendation = mapper.toEntity(request);

        recommendation.setUser(user);
        recommendation.setJobPosting(jobPosting);
        recommendation.setCreatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(recommendation));
    }

    @Override
    public JobRecommendationResponse getById(Long id) {
        JobRecommendation recommendation = getOwnedRecommendation(id);

        return mapper.toResponse(recommendation);
    }

    @Override
    public List<JobRecommendationResponse> getMine() {
        return repository.findByUserId(currentUserService.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<JobRecommendationResponse> getByJobPostingId(Long jobPostingId) {
        JobPosting posting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosting with id '" + jobPostingId + "' not found."));
        if (posting.getCompany().getUser() == null
                || !posting.getCompany().getUser().getId().equals(currentUserService.getId())) {
            throw new AccessDeniedException("You do not own this company.");
        }
        return repository.findByJobPostingId(jobPostingId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public JobRecommendationResponse update(Long id, JobRecommendationRequest request) {
        JobRecommendation recommendation = getOwnedRecommendation(id);

        recommendation.setMatchScore(request.getMatchScore());
        recommendation.setExplanation(request.getExplanation());

        return mapper.toResponse(repository.save(recommendation));
    }

    @Override
    public void delete(Long id) {
        JobRecommendation recommendation = getOwnedRecommendation(id);

        repository.delete(recommendation);
    }

    private JobRecommendation getOwnedRecommendation(Long id) {
        JobRecommendation recommendation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobRecommendation with id '" + id + "' not found."));
        if (!recommendation.getUser().getId().equals(currentUserService.getId())) {
            throw new ResourceNotFoundException("JobRecommendation with id '" + id + "' not found.");
        }
        return recommendation;
    }
}