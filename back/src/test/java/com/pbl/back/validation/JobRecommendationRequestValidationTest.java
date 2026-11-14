package com.pbl.back.validation;

import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import org.junit.jupiter.api.Test;

class JobRecommendationRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesPostingScoreAndExplanation() {
        JobRecommendationRequest request = new JobRecommendationRequest();
        assertInvalid(request, "jobPostingId", "required");
        request.setJobPostingId(1L);
        request.setMatchScore(-0.1);
        assertInvalid(request, "matchScore", "0.0");
        request.setMatchScore(100.1);
        assertInvalid(request, "matchScore", "100.0");
        request.setMatchScore(100.0);
        request.setExplanation("x".repeat(2001));
        assertInvalid(request, "explanation", "2000");
        request.setExplanation("");
        assertValid(request);
    }
}
