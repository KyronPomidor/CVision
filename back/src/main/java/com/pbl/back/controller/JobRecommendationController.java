package com.pbl.back.controller;

import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.service.JobRecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
public class JobRecommendationController {

    private final JobRecommendationService service;

    public JobRecommendationController(JobRecommendationService service) {
        this.service = service;
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public JobRecommendationResponse create(@Valid @RequestBody JobRecommendationRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public JobRecommendationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/me")
    public List<JobRecommendationResponse> getMine() {
        return service.getMine();
    }

    @GetMapping("/job/{jobPostingId}")
    public List<JobRecommendationResponse> getByJobPostingId(@PathVariable Long jobPostingId) {
        return service.getByJobPostingId(jobPostingId);
    }

    @PutMapping("/{id}")
    public JobRecommendationResponse update(@PathVariable Long id,
            @Valid @RequestBody JobRecommendationRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}