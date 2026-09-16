package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobRecommendation;
import com.pbl.back.domain.entity.User;
import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.JobRecommendationMapper;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.JobPostingSkillRepository;
import com.pbl.back.repository.JobRecommendationRepository;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.CVSkillRepository;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.FitScoreCalculator;
import com.pbl.back.service.JobRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobRecommendationServiceImpl implements JobRecommendationService {

    private final JobRecommendationRepository repository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobPostingSkillRepository jobPostingSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final CVRepository cvRepository;
    private final CVSkillRepository cvSkillRepository;
    private final FitScoreCalculator fitScoreCalculator;
    private final JobRecommendationMapper mapper;

    @Override
    public JobRecommendationResponse create(Long userId, JobRecommendationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

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
        JobRecommendation recommendation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobRecommendation with id '" + id + "' not found."));

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
    @Transactional
    public List<JobRecommendationResponse> generateForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        Set<String> candidateSkills = getCandidateSkills(userId);

        return jobPostingRepository.findByStatus(JobStatus.OPEN).stream()
                .map(job -> calculateAndSave(user, candidateSkills, job))
                .sorted(Comparator.comparing(JobRecommendationResponse::getMatchScore).reversed())
                .toList();
    }

    @Override
    public JobRecommendationResponse update(Long id, JobRecommendationRequest request) {
        JobRecommendation recommendation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobRecommendation with id '" + id + "' not found."));

        recommendation.setMatchScore(request.getMatchScore());
        recommendation.setExplanation(request.getExplanation());

        return mapper.toResponse(repository.save(recommendation));
    }

    @Override
    public void delete(Long id) {
        JobRecommendation recommendation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobRecommendation with id '" + id + "' not found."));

        repository.delete(recommendation);
    }

    private Set<String> getCandidateSkills(Long userId) {
        Set<String> skills = new LinkedHashSet<>();

        candidateProfileRepository.findByUserId(userId)
                .ifPresent(profile -> profileSkillRepository.findByProfileId(profile.getId())
                        .forEach(profileSkill -> skills.add(profileSkill.getSkill().getName())));

        cvRepository.findByUserId(userId)
                .ifPresent(cv -> cvSkillRepository.findByCvId(cv.getId())
                        .forEach(cvSkill -> skills.add(cvSkill.getSkill().getName())));

        return skills;
    }

    private JobRecommendationResponse calculateAndSave(User user, Collection<String> candidateSkills, JobPosting job) {
        List<String> jobSkills = jobPostingSkillRepository.findByJobPostingId(job.getId()).stream()
                .map(jobPostingSkill -> jobPostingSkill.getSkill().getName())
                .toList();

        FitScoreCalculator.FitScoreResult result = fitScoreCalculator.calculate(candidateSkills, jobSkills);
        JobRecommendation recommendation = repository
                .findFirstByUserIdAndJobPostingIdOrderByCreatedAtDesc(user.getId(), job.getId())
                .orElseGet(JobRecommendation::new);

        recommendation.setUser(user);
        recommendation.setJobPosting(job);
        recommendation.setMatchScore(result.score());
        recommendation.setExplanation(buildExplanation(result, jobSkills.isEmpty()));
        recommendation.setCreatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(recommendation));
    }

    private String buildExplanation(FitScoreCalculator.FitScoreResult result, boolean jobHasNoSkills) {
        if (jobHasNoSkills) {
            return "This job has no listed skills yet, so a fit score cannot be calculated.";
        }
        if (result.matchedSkills().isEmpty()) {
            return "No listed job skills were found in the candidate profile or CV.";
        }

        String matched = String.join(", ", result.matchedSkills());
        if (result.missingSkills().isEmpty()) {
            return "Matches all listed job skills: " + matched + ".";
        }
        return "Matched skills: " + matched + ". Missing skills: "
                + String.join(", ", result.missingSkills()) + ".";
    }
}
