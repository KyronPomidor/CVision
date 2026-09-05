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

    @PostMapping("/user/{userId}")
    public CandidateProfileResponse create(@PathVariable Long userId,
                                           @Valid @RequestBody CandidateProfileRequest request) {
        return service.create(userId, request);
    }

    @GetMapping("/user/{userId}")
    public CandidateProfileResponse getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PutMapping("/{id}")
    public CandidateProfileResponse update(@PathVariable Long id, @Valid @RequestBody CandidateProfileRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public CandidateProfileResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
