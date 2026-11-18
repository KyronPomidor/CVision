package com.pbl.back.validation;

import com.pbl.back.dto.jobposting.JobPostingDetailsRequest;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

class JobPostingRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesPostingAndDetailsFields() {
        JobPostingRequest request = validRequest();
        request.setDetails(null);
        assertInvalid(request, "details", "required");
        request.setDetails(new JobPostingDetailsRequest());
        request.setTitle(" ");
        assertInvalid(request, "title", "required");
        request.setTitle("ab");
        assertInvalid(request, "title", "3-150");
        request.setTitle("Job title");
        request.setTitle("x".repeat(151));
        assertInvalid(request, "title", "3-150");
        request.setTitle("Job title");
        request.setDescription("x".repeat(10001));
        assertInvalid(request, "description", "10000");
        request.setDescription("");
        request.setSkillIds(null);
        assertValid(request);
        org.junit.jupiter.api.Assertions.assertTrue(request.getSkillIds().isEmpty());

        request.setSkillIds(Collections.nCopies(51, 1L));
        assertInvalid(request, "skillIds", "50");
        request.setSkillIds(Arrays.asList(1L, null));
        assertInvalid(request, "skillIds[1].<list element>", "must not be null");
        request.setSkillIds(Arrays.asList(0L));
        assertInvalid(request, "skillIds[0].<list element>", "greater than 0");
        request.setSkillIds(Arrays.asList(1L));

        JobPostingDetailsRequest details = request.getDetails();
        details.setSalary(new BigDecimal("12345678901.00"));
        assertInvalid(request, "details.salary", "10 integer");
        details.setSalary(BigDecimal.valueOf(-1));
        assertInvalid(request, "details.salary", "greater than or equal to 0");
        details.setSalary(BigDecimal.ZERO);
        details.setExperience(61);
        assertInvalid(request, "details.experience", "60");
        details.setExperience(-1);
        assertInvalid(request, "details.experience", "greater than or equal to 0");
        details.setExperience(1);
        details.setLocation("x".repeat(256));
        assertInvalid(request, "details.location", "255");
        details.setLocation("");
        details.setEducation("x".repeat(256));
        assertInvalid(request, "details.education", "255");
        details.setEducation("");
        details.setContactEmail("a@b");
        assertInvalid(request, "details.contactEmail", "valid email");
        details.setContactEmail("");
        assertValid(request);
    }

    private JobPostingRequest validRequest() {
        JobPostingRequest request = new JobPostingRequest();
        request.setTitle("Job title");
        request.setDetails(new JobPostingDetailsRequest());
        return request;
    }
}
