package com.pbl.back.controller;

import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.service.JobRecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class JobRecommendationController {

    private final JobRecommendationService service;

    public JobRecommendationController(JobRecommendationService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public JobRecommendationResponse create(@PathVariable Long userId,
            @Valid @RequestBody JobRecommendationRequest request) {
        return service.create(userId, request);
    }

    @GetMapping("/{id}")
    public JobRecommendationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<JobRecommendationResponse> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
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