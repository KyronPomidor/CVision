package com.pbl.back.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExtractedSkill(@NotBlank @Size(max = 100) String name, Integer yearsOfExperience) {
    public ExtractedSkill {
        if (yearsOfExperience != null && (yearsOfExperience < 0 || yearsOfExperience > 60)) {
            throw new IllegalArgumentException("Skill experience must be between 0 and 60 years");
        }
    }
}
