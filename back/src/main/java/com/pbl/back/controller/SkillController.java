package com.pbl.back.controller;

import com.pbl.back.dto.skill.SkillRequest;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService service;

    @PostMapping
    public SkillResponse create(
            @Valid @RequestBody SkillRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public SkillResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<SkillResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public SkillResponse update(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}