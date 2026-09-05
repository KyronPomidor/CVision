package com.pbl.back.dto.cv;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVRequest {

    @NotBlank
    private String fileName;

    @NotBlank
    private String filePath;

    private String extractedText;
}