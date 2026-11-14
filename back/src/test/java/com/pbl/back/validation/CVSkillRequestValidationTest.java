package com.pbl.back.validation;

import com.pbl.back.dto.cvskill.CVSkillRequest;
import org.junit.jupiter.api.Test;

class CVSkillRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesSkillConfidenceAndExperience() {
        CVSkillRequest request = new CVSkillRequest();
        assertInvalid(request, "skillId", "required");
        request.setSkillId(1L);
        request.setConfidenceScore(-0.1);
        assertInvalid(request, "confidenceScore", "0.0");
        request.setConfidenceScore(1.1);
        assertInvalid(request, "confidenceScore", "1.0");
        request.setConfidenceScore(1.0);
        request.setYearsOfExperience(-1);
        assertInvalid(request, "yearsOfExperience", "greater than or equal to 0");
        request.setYearsOfExperience(61);
        assertInvalid(request, "yearsOfExperience", "60");
        request.setYearsOfExperience(0);
        assertValid(request);
    }
}
