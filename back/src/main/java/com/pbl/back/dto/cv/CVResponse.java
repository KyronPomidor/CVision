package com.pbl.back.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
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
    private List<Language> languages;
    private String objective;
    private List<Experience> experience;
    private String additionalInfo;
    private LocalDateTime uploadedAt;

    public record Language(String name, String level) {
    }

    public record Experience(Long id, String title, String company, String location,
                             LocalDate startDate, LocalDate endDate, String description) {
    }
}