package com.pbl.back.dto.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDetailsRequest {

    private String website;
    private String email;
    private String address;
    private Integer size;
    private Integer founded;
    private String linkedin;
    private String instagram;
    private String twitter;
    private String facebook;
}
