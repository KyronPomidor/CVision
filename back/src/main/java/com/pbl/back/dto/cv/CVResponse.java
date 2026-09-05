package com.pbl.back.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CVResponse {

    private Long id;
    private Long userId;
    private String fileName;
    private String filePath;
    private String extractedText;
    private LocalDateTime uploadedAt;
}