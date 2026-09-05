package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.mapper.CVMapper;
import org.springframework.stereotype.Component;

@Component
public class CVMapperImpl implements CVMapper {
    @Override
    public CV toEntity(CVRequest request) {
        return CV.builder()
                .fileName(request.getFileName())
                .filePath(request.getFilePath())
                .extractedText(request.getExtractedText())
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
                .uploadedAt(cv.getUploadedAt())
                .build();
    }
}
