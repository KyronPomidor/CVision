package com.pbl.back.controller;

import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postings")
public class JobPostingController {
    private final JobPostingService service;
    private final CurrentUserService currentUserService;

    public JobPostingController(JobPostingService service, CurrentUserService currentUserService) {
        this.service = service;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/company/{companyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public JobPostingResponse create(@PathVariable Long companyId,
                                     @Valid @RequestBody JobPostingRequest request) {
        return service.create(companyId, request);
    }

    @GetMapping("/company/{companyId}")
    public List<JobPostingResponse> getByUserId(@PathVariable Long companyId) {
        return service.getByCompanyId(companyId);
    }

    @GetMapping("/matches/me")
    public List<JobPostingResponse> getMyMatches() {
        return service.getMatchesForUser(currentUserService.getId());
    }


    @PutMapping("/{id}")
    public JobPostingResponse update(@PathVariable Long id, @Valid @RequestBody JobPostingRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    public JobPostingResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{postingId}/images")
    public JobPostingResponse uploadImages(@PathVariable Long postingId,
                                           @RequestParam("files") List<MultipartFile> files) {
        return service.uploadImages(postingId, files);
    }

    @GetMapping("/{postingId}/images/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long postingId, @PathVariable Long imageId) {
        return service.getImage(postingId, imageId);
    }

    @DeleteMapping("/{postingId}/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long postingId, @PathVariable Long imageId) {
        service.deleteImage(postingId, imageId);
    }
}
