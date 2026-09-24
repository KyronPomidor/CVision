package com.pbl.back.dto.gemini;

import jakarta.validation.constraints.NotBlank;

public record GeminiQuestionRequest(
        @NotBlank(message = "Question must not be blank")
        String question
) {
}
