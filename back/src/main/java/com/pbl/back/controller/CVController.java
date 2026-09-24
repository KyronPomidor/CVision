package com.pbl.back.controller;

import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.service.CVService;
import com.pbl.back.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cv")
public class CVController {
    private final CVService service;
    private final CurrentUserService currentUserService;

    public CVController(CVService service, CurrentUserService currentUserService) {
        this.service = service;
        this.currentUserService = currentUserService;
    }

    @PostMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CVResponse create(@Valid @RequestBody CVRequest request) {
        return service.create(currentUserService.getId(), request);
    }

    @PostMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CVResponse upload(@RequestParam("file") MultipartFile file) {
        return service.upload(currentUserService.getId(), file);
    }

    @GetMapping("/me")
    public CVResponse getMyCv() {
        return service.getByUserId(currentUserService.getId());
    }

    @PutMapping("/{id}")
    public CVResponse update(@PathVariable Long id, @Valid @RequestBody CVRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    public CVResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
