package com.pbl.back.dto.cv;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CVLanguage(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Pattern(regexp = "A1|A2|B1|B2|C1|C2|Native") String level
) {
}
