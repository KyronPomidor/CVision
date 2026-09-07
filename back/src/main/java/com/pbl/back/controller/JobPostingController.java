package com.pbl.back.controller;

import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/postings")
public class JobPostingController {
    private final JobPostingService service;

    public JobPostingController(JobPostingService service) {
        this.service = service;
    }

    @PostMapping("/company/{companyId}")
    public JobPostingResponse create(@PathVariable Long companyId,
                                     @Valid @RequestBody JobPostingRequest request) {
        return service.create(companyId, request);
    }

    @GetMapping("/company/{companyId}")
    public JobPostingResponse getByUserId(@PathVariable Long companyId) {
        return service.getByCompanyId(companyId);
    }

    @PutMapping("/{id}")
    public JobPostingResponse update(@PathVariable Long id, @Valid @RequestBody JobPostingRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public JobPostingResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
