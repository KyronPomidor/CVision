package com.pbl.back.dto.cv;

import com.pbl.back.dto.skill.ExtractedSkill;
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
    private List<ExtractedSkill> skillExperience;
    private List<CVLanguage> languages;
    private String objective;
    private List<CVExperience> experience;
    private String additionalInfo;
    private LocalDateTime uploadedAt;
}