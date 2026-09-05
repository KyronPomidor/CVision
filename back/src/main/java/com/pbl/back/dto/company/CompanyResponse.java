package com.pbl.back.dto.company;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponse {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String website;
    private String location;
}
