package com.pbl.back.validation;

import com.pbl.back.domain.enums.SkillLevel;
import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import org.junit.jupiter.api.Test;

class ProfileSkillRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesRequiredReferencesAndExperience() {
        ProfileSkillRequest request = new ProfileSkillRequest();
        assertInvalid(request, "skillId", "required");
        assertInvalid(request, "level", "required");
        request.setSkillId(1L);
        request.setLevel(SkillLevel.BEGINNER);
        request.setYearsOfExperience(-1);
        assertInvalid(request, "yearsOfExperience", "greater than or equal to 0");
        request.setYearsOfExperience(61);
        assertInvalid(request, "yearsOfExperience", "60");
        request.setYearsOfExperience(0);
        assertValid(request);
    }
}
