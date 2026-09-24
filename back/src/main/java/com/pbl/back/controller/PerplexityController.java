package com.pbl.back.controller;

import com.pbl.back.dto.gemini.GeminiAnswerResponse;
import com.pbl.back.dto.gemini.GeminiQuestionRequest;
import com.pbl.back.service.PerplexityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/perplexity")
public class PerplexityController {

    private final PerplexityService perplexityService;

    public PerplexityController(PerplexityService perplexityService) {
        this.perplexityService = perplexityService;
    }

    @PostMapping("/ask")
    public GeminiAnswerResponse ask(@Valid @RequestBody GeminiQuestionRequest request) {
        return new GeminiAnswerResponse(perplexityService.answer(request.question()));
    }
}
