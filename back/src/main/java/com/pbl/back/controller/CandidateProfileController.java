package com.pbl.back.controller;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.service.CandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class CandidateProfileController {

    private final CandidateProfileService service;

    public CandidateProfileController(CandidateProfileService service) {
        this.service = service;
    }

    @PostMapping
    public CandidateProfileResponse create(@Valid @RequestBody CandidateProfileRequest request) {
        return service.create(request);
    }

    @GetMapping("/user/{userId}")
    public CandidateProfileResponse getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PutMapping("/user/{userId}")
    public CandidateProfileResponse update(@PathVariable Long userId, @Valid @RequestBody CandidateProfileRequest request) {
        return service.update(userId, request);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteByUserId(@PathVariable Long userId) {
        service.delete(userId);
    }
}
