package com.pbl.back.dto.cv;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVRequest {

    @NotBlank(message = "is required")
    @Size(max = 255, message = "must be at most 255 characters")
    private String fileName;

    @NotBlank(message = "is required")
    @Size(max = 1024, message = "must be at most 1024 characters")
    private String filePath;

    @Size(max = 50000, message = "must be at most 50000 characters")
    private String extractedText;
}