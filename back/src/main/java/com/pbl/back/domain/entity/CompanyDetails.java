package com.pbl.back.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetails {

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