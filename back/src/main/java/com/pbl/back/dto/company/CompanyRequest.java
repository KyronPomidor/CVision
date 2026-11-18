package com.pbl.back.dto.company;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyRequest {

    @NotBlank(message = "is required")
    @Size(min = 2, max = 100, message = "must be 2-100 characters")
    private String name;

    @Size(max = 5000, message = "must be at most 5000 characters")
    private String description;

    @Size(max = 255, message = "must be at most 255 characters")
    private String location;

    @Size(max = 1024, message = "must be at most 1024 characters")
    private String logo;

    @Size(max = 20, message = "must contain at most 20 photos")
    private List<@Size(max = 1024, message = "must be at most 1024 characters") String> photos;

    @NotNull(message = "is required")
    @Valid
    private CompanyDetailsRequest details;
}
