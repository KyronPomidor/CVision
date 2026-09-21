package com.pbl.back.dto.company;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CompanyResponse {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String location;
    private List<String> photos;
    private CompanyDetailsResponse details;
}
