package com.pbl.back.validation;

import com.pbl.back.dto.skill.SkillRequest;
import org.junit.jupiter.api.Test;

class SkillRequestValidationTest extends ValidationTestSupport {

    @Test
    void acceptsShortSkillNamesAndRejectsBlankOrOverlongNames() {
        SkillRequest request = new SkillRequest();
        request.setName("");
        assertInvalid(request, "name", "required");
        request.setName("C");
        assertValid(request);
        request.setName("x".repeat(101));
        assertInvalid(request, "name", "100");
    }
}
