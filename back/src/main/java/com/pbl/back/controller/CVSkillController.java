package com.pbl.back.controller;

import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;
import com.pbl.back.service.CVSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv/me/skills")
public class CVSkillController {

    private final CVSkillService service;

    public CVSkillController(CVSkillService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CVSkillResponse create(@Valid @RequestBody CVSkillRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CVSkillResponse> getMine() {
        return service.getMine();
    }

    @GetMapping("/{id}")
    public CVSkillResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public CVSkillResponse update(@PathVariable Long id, @Valid @RequestBody CVSkillRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}