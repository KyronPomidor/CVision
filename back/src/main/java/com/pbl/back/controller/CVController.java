package com.pbl.back.controller;

import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.service.CVService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cv")
public class CVController {
    private final CVService service;

    public CVController(CVService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CVResponse create(@PathVariable Long userId, @Valid @RequestBody CVRequest request) {
        return service.create(userId, request);
    }

    @GetMapping("/user/{userId}")
    public CVResponse getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
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
