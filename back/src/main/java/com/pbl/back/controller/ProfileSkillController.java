package com.pbl.back.controller;

import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.service.ProfileSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileSkillController {

    private final ProfileSkillService service;

    ProfileSkillController(ProfileSkillService service) {
        this.service = service;
    }

    @PostMapping("/me/skills")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileSkillResponse addSkill(@Valid @RequestBody ProfileSkillRequest request) {
        return service.addSkill(request);
    }

    @GetMapping("/me/skills")
    public List<ProfileSkillResponse> getSkills() {
        return service.getSkills();
    }

    @GetMapping("/me/skills/{id}")
    public ProfileSkillResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/me/skills/{id}")
    public ProfileSkillResponse update(@PathVariable Long id, @Valid @RequestBody ProfileSkillRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/me/skills/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}