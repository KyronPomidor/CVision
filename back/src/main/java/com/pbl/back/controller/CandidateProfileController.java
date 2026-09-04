package com.pbl.back.controller;

import com.pbl.back.dto.CandidateProfileRequest;
import com.pbl.back.dto.CandidateProfileResponse;
import com.pbl.back.service.CandidateProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
