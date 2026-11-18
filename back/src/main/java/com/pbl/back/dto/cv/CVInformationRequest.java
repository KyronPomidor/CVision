package com.pbl.back.dto.cv;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVInformationRequest {

    @Size(max = 5000, message = "must be at most 5000 characters")
    private String objective;

    @Size(max = 100)
    private List<@Size(max = 100) String> skills;

    private List<CVResponse.Language> languages;

    private List<CVResponse.Experience> experience;

    @Size(max = 5000, message = "must be at most 5000 characters")
    private String additionalInfo;
}
