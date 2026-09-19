package com.pbl.back.controller;

import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.service.CVService;
import com.pbl.back.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cv")
public class CVController {
    private final CVService service;
    private final CurrentUserService currentUserService;

    public CVController(CVService service, CurrentUserService currentUserService) {
        this.service = service;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public CVResponse create(@Valid @RequestBody CVRequest request) {
        return service.create(currentUserService.getId(), request);
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
