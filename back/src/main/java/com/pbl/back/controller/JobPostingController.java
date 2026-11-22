package com.pbl.back.controller;

import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.application.ApplicationResponse;
import com.pbl.back.service.ApplicationService;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobPostingController {
    private final JobPostingService service;
    private final CurrentUserService currentUserService;
    private final ApplicationService applicationService;

    public JobPostingController(JobPostingService service, CurrentUserService currentUserService,
                                ApplicationService applicationService) {
        this.service = service;
        this.currentUserService = currentUserService;
        this.applicationService = applicationService;
    }

    @PostMapping("/companies/me/postings")
    @ResponseStatus(HttpStatus.CREATED)
    public JobPostingResponse create(@Valid @RequestBody JobPostingRequest request) {
        return service.createForCurrentCompany(request);
    }

    @GetMapping("/companies/me/postings")
    public List<JobPostingResponse> getMine() {
        return service.getForCurrentCompany();
    }

    @GetMapping("/postings/matches/me")
    public List<JobPostingResponse> getMyMatches() {
        return service.getMatchesForUser(currentUserService.getId());
    }


    @PutMapping("/postings/{id}")
    public JobPostingResponse update(@PathVariable Long id, @Valid @RequestBody JobPostingRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/postings/{id}")
    public JobPostingResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/postings/{id}/applicants")
    public List<ApplicationResponse> getApplicants(@PathVariable Long id) {
        return applicationService.getApplicantsForPosting(id);
    }

    @DeleteMapping("/postings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping(value = "/postings/{postingId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JobPostingResponse uploadImages(@PathVariable Long postingId,
                                           @RequestPart("files") List<MultipartFile> files) {
        return service.uploadImages(postingId, files);
    }

    @GetMapping("/postings/{postingId}/images/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long postingId, @PathVariable Long imageId) {
        return service.getImage(postingId, imageId);
    }

    @DeleteMapping("/postings/{postingId}/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long postingId, @PathVariable Long imageId) {
        service.deleteImage(postingId, imageId);
    }
}
