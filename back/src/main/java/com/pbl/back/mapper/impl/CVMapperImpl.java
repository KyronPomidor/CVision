package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.mapper.CVMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CVMapperImpl implements CVMapper {
    private final ObjectMapper objectMapper;

    public CVMapperImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CV toEntity(CVRequest request) {
        return CV.builder()
                .fileName(request.getFileName())
                .filePath(request.getFilePath())
                .extractedText(request.getExtractedText())
                .uploadedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public CVResponse toResponse(CV cv) {
        return CVResponse.builder()
                .id(cv.getId())
                .userId(cv.getUser().getId())
                .fileName(cv.getFileName())
                .filePath(cv.getFilePath())
                .extractedText(cv.getExtractedText())
                .skills(parseSkills(cv.getSkills()))
                .uploadedAt(cv.getUploadedAt())
                .build();
    }

    private List<String> parseSkills(String skillsJson) {
        if (skillsJson == null || skillsJson.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(skillsJson, new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Stored CV skills are not valid JSON", ex);
        }
    }
}
