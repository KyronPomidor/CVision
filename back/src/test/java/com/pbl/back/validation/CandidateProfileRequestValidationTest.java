package com.pbl.back.validation;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import org.junit.jupiter.api.Test;

class CandidateProfileRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesRequiredNameAndOptionalFieldLengths() {
        CandidateProfileRequest request = validRequest();
        request.setName(" ");
        assertInvalid(request, "name", "required");
        request.setName("ab");
        assertInvalid(request, "name", "3-100");
        request.setName("Candidate");
        assertValid(request);
        request.setName("x".repeat(101));
        assertInvalid(request, "name", "3-100");
        request.setName("Candidate");

        request.setLocation("x".repeat(256));
        assertInvalid(request, "location", "255");
        request.setLocation("");
        request.setEducation("");
        request.setExperience("");
        request.setDescription("");
        assertValid(request);

        request.setEducation("x".repeat(256));
        assertInvalid(request, "education", "255");
        request.setEducation("");
        request.setExperience("x".repeat(5001));
        assertInvalid(request, "experience", "5000");
        request.setExperience("");
        request.setDescription("x".repeat(5001));
        assertInvalid(request, "description", "5000");
    }

    private CandidateProfileRequest validRequest() {
        CandidateProfileRequest request = new CandidateProfileRequest();
        request.setName("Candidate");
        return request;
    }
}
