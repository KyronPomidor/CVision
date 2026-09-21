package com.pbl.back.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyRequest {

    @NotBlank
    private String name;
    private String description;
    private String location;
    private List<String> photos;
    private CompanyDetailsRequest details;
}
