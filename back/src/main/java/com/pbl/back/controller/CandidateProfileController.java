package com.pbl.back.controller;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.service.CandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class CandidateProfileController {

    private final CandidateProfileService service;

    public CandidateProfileController(CandidateProfileService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
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

    @GetMapping("/{id}")
    public CandidateProfileResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping(value = "/me/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CandidateProfileResponse uploadPicture(@RequestPart("file") MultipartFile file) {
        return service.uploadPicture(file);
    }

    @GetMapping("/{id}/picture")
    public ResponseEntity<Resource> getPicture(@PathVariable Long id) {
        return service.getPicture(id);
    }

    @DeleteMapping("/me/picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePicture() {
        service.deletePicture();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
