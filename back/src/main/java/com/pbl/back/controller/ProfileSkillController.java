package com.pbl.back.controller;

import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.service.ProfileSkillService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileSkillController {

    private final ProfileSkillService service;

    ProfileSkillController(ProfileSkillService service) {
        this.service = service;
    }

    @PostMapping("/{profileId}/skills")
    public ProfileSkillResponse addSkill(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileSkillRequest request) {
        return service.addSkill(profileId, request);
    }

    @GetMapping("/{profileId}/skills")
    public List<ProfileSkillResponse> getSkills(@PathVariable Long profileId) {
        return service.getSkills(profileId);
    }

    @GetMapping("/skills/{id}")
    public ProfileSkillResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/skills/{id}")
    public ProfileSkillResponse update(@PathVariable Long id, @Valid @RequestBody ProfileSkillRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/skills/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}