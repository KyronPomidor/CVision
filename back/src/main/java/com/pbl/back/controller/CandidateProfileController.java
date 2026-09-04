package com.pbl.back.controller;

import com.pbl.back.dto.CandidateProfileRequest;
import com.pbl.back.dto.CandidateProfileResponse;
import com.pbl.back.service.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class CandidateProfileController {

    private final CandidateProfileService service;

    @PostMapping
    public CandidateProfileResponse create(
            @RequestBody CandidateProfileRequest request) {

        return service.create(request);
    }
}
