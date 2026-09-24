package com.pbl.back.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CVResponse {

    private Long id;
    private Long userId;
    private String fileName;
    private String filePath;
    private String extractedText;
    private List<String> skills;
    private LocalDateTime uploadedAt;
}