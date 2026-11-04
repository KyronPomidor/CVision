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
import com.pbl.back.service.ApplicationService;
import com.pbl.back.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper mapper;
    private final ApplicationRepository repository;
    private final CVRepository cvRepository;
    private final JobPostingRepository jobPostingRepository;
    private final CurrentUserService currentUserService;

    @Override
    public ApplicationResponse create(ApplicationRequest request) {
        User user = currentUserService.get();

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobPosting with id '" + request.getJobPostingId() + "' not found."));

        CV cv = cvRepository.findById(request.getCvId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CV with id '" + request.getCvId() + "' not found."));
        if (!cv.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only apply with your own CV.");
        }

        Application application = mapper.toEntity(request);

        application.setUser(user);
        application.setCv(cv);
        application.setJobPosting(jobPosting);
        application.setAppliedAt(LocalDateTime.now());

        Application savedApplication = repository.save(application);

        return mapper.toResponse(savedApplication);
    }

    @Override
    public ApplicationResponse getMine() {
        Application application = repository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application for the authenticated user was not found."));
        return mapper.toResponse(application);
    }

    @Override
    public ApplicationResponse getById(Long id) {
        return mapper.toResponse(getOwnedApplication(id));
    }

    @Override
    public ApplicationResponse update(Long id, ApplicationRequest request) {
        Application application = getOwnedApplication(id);

        application.setStatus(request.getStatus());

        return mapper.toResponse(repository.save(application));
    }

    @Override
    public void delete(Long id) {
        repository.delete(getOwnedApplication(id));
    }

    private Application getOwnedApplication(Long id) {
        Application application = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application with id '" + id + "' not found."));
        if (!application.getUser().getId().equals(currentUserService.getId())) {
            throw new AccessDeniedException("You do not own this application.");
        }
        return application;
    }
}
