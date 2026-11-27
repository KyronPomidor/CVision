package com.pbl.back.mapper.impl;

import com.pbl.back.dto.skill.ExtractedSkill;
import com.pbl.back.repository.CVSkillRepository;
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

    private final CVSkillRepository skillRepository;

    public CVMapperImpl(ObjectMapper objectMapper, CVSkillRepository skillRepository) {
        this.skillRepository = skillRepository;
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
                .skills(parseList(cv.getSkills(), new TypeReference<>() {}))
                .skillExperience(skillRepository.findByCvId(cv.getId()).stream()
                        .map(link -> new ExtractedSkill(link.getSkill().getName(),
                                link.getYearsOfExperience()))
                        .toList())
                .languages(parseList(cv.getLanguages(), new TypeReference<>() {}))
                .objective(cv.getObjective())
                .experience(parseList(cv.getExperience(), new TypeReference<>() {}))
                .additionalInfo(cv.getAdditionalInfo())
                .uploadedAt(cv.getUploadedAt())
                .build();
    }

    private <T> List<T> parseList(String json, TypeReference<List<T>> type) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Stored CV data is not valid JSON", ex);
        }
    }
}
