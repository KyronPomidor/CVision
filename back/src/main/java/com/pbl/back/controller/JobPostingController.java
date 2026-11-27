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
import java.util.Comparator;
import java.util.Locale;

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

    public record MatchPage(List<JobPostingResponse> items, int page, int totalPages) {}

    @GetMapping("/postings/matches/me")
    public MatchPage getMyMatches(@RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long companyId, @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "Compatibility") String sort,
            @RequestParam(required = false) List<String> jobType,
            @RequestParam(required = false) List<String> schedule,
            @RequestParam(required = false) List<String> workSetting) {
        if (page < 0) throw new org.springframework.web.server.ResponseStatusException(HttpStatus.BAD_REQUEST);
        var order = switch (sort) {
            case "Salary" -> Comparator.comparing((JobPostingResponse p) -> p.getDetails() == null ? null : p.getDetails().getSalary(),
                    Comparator.nullsLast(Comparator.reverseOrder()));
            case "Recency" -> Comparator.comparing(JobPostingResponse::getCreatedAt, Comparator.reverseOrder());
            default -> Comparator.comparing(JobPostingResponse::getMatchScore, Comparator.reverseOrder());
        };
        var matches = service.getMatchesForUser(currentUserService.getId()).stream()
                .filter(p -> companyId == null || companyId.equals(p.getCompany().getId()))
                .filter(p -> {
                    var d = p.getDetails();
                    String text = p.getTitle() + " " + p.getCompany().getName() + " " + (d == null ? "" : d.getLocation());
                    return text.toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT))
                            && (jobType == null || d != null && jobType.contains(String.valueOf(d.getJobType())))
                            && (schedule == null || d != null && schedule.contains(String.valueOf(d.getSchedule())))
                            && (workSetting == null || d != null && workSetting.contains(String.valueOf(d.getWorkSetting())));
                })
                .sorted(order.thenComparing(JobPostingResponse::getId)).toList();
        int pages = Math.ceilDiv(matches.size(), 10);
        int current = Math.min(page, Math.max(0, pages - 1));
        int start = current * 10;
        return new MatchPage(matches.subList(start, start + Math.min(10, matches.size() - start)), current, pages);
    }


    @PutMapping("/postings/{id}")
    public JobPostingResponse update(@PathVariable Long id, @Valid @RequestBody JobPostingRequest request) {
        return service.update(id, request);
    }

    public record RequiredYears(@jakarta.validation.constraints.NotNull
            @jakarta.validation.constraints.Min(0) @jakarta.validation.constraints.Max(60) Integer years) {}

    @PutMapping("/postings/{id}/skills/{skillId}/experience")
    public JobPostingResponse updateRequiredYears(@PathVariable Long id, @PathVariable Long skillId,
            @Valid @RequestBody RequiredYears request) {
        return service.updateRequiredYears(id, skillId, request.years());
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
