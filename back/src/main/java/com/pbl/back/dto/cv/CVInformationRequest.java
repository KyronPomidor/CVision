package com.pbl.back.dto.cv;

import com.pbl.back.dto.skill.ExtractedSkill;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CVInformationRequest {

    @NotNull
    @Size(max = 100)
    private List<@NotBlank @Size(max = 100) String> skills = List.of();

    @Size(max = 100)
    private List<@NotNull @Valid ExtractedSkill> skillExperience;

    @NotNull
    @Valid
    private List<@NotNull CVLanguage> languages = List.of();

    @Size(max = 5000)
    private String objective;

    @NotNull
    @Valid
    private List<@NotNull CVExperience> experience = List.of();

    @Size(max = 5000)
    private String additionalInfo;
}
