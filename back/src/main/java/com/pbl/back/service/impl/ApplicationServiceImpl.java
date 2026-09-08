package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.Application;
import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.application.ApplicationRequest;
import com.pbl.back.dto.application.ApplicationResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.ApplicationMapper;
import com.pbl.back.repository.ApplicationRepository;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper mapper;
    private final ApplicationRepository repository;
    private final UserRepository userRepository;
    private final CVRepository cvRepository;
    private final JobPostingRepository jobPostingRepository;

    @Override
    public ApplicationResponse create(Long userId, ApplicationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobPosting with id '" + request.getJobPostingId() + "' not found."));

        CV cv = cvRepository.findById(request.getCvId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CV with id '" + request.getCvId() + "' not found."));

        Application application = mapper.toEntity(request);

        application.setUser(user);
        application.setCv(cv);
        application.setJobPosting(jobPosting);
        application.setAppliedAt(LocalDateTime.now());

        Application savedApplication = repository.save(application);

        return mapper.toResponse(savedApplication);
    }

    @Override
    public ApplicationResponse getByUserId(Long userId) {
        Application application = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application for user with id '" + userId + "' not found."));
        return mapper.toResponse(application);
    }

    @Override
    public ApplicationResponse getById(Long id) {
        Application application = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application with id '" + id + "' not found."));
        return mapper.toResponse(application);
    }

    @Override
    public ApplicationResponse update(Long id, ApplicationRequest request) {
        Application application = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application with id '" + id + "' not found."));

        application.setStatus(request.getStatus());

        return mapper.toResponse(repository.save(application));
    }

    @Override
    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application with id '" + id + "' not found."))
        );
    }
}
