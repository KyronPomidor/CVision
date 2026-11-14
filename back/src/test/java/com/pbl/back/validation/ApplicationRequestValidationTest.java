package com.pbl.back.validation;

import com.pbl.back.domain.enums.ApplicationStatus;
import com.pbl.back.dto.application.ApplicationRequest;
import org.junit.jupiter.api.Test;

class ApplicationRequestValidationTest extends ValidationTestSupport {

    @Test
    void requiresPostingCvAndStatus() {
        ApplicationRequest request = new ApplicationRequest();
        assertInvalid(request, "jobPostingId", "required");
        assertInvalid(request, "cvId", "required");
        assertInvalid(request, "status", "required");
        request.setJobPostingId(1L);
        request.setCvId(2L);
        request.setStatus(ApplicationStatus.APPLIED);
        assertValid(request);
    }
}
