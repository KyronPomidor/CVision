package com.pbl.back.service;

import org.springframework.web.multipart.MultipartFile;

public interface PerplexityService {

    String answer(String question);

    String extractSkills(MultipartFile file);
}
