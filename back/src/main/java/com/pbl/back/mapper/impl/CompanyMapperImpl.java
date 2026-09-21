package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.CompanyDetails;
import com.pbl.back.dto.company.CompanyDetailsResponse;
import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.mapper.CompanyMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public Company toEntity(CompanyRequest request) {

        CompanyDetails details = CompanyDetails.builder()
                .website(request.getDetails().getWebsite())
                .email(request.getDetails().getEmail())
                .address(request.getDetails().getAddress())
                .size(request.getDetails().getSize())
                .founded(request.getDetails().getFounded())
                .linkedin(request.getDetails().getLinkedin())
                .instagram(request.getDetails().getInstagram())
                .twitter(request.getDetails().getTwitter())
                .facebook(request.getDetails().getFacebook())
                .build();

        return Company.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .details(details)
                .build();
    }

    @Override
    public CompanyResponse toResponse(Company company) {

        CompanyDetailsResponse details = CompanyDetailsResponse.builder()
                .website(company.getDetails().getWebsite())
                .email(company.getDetails().getEmail())
                .address(company.getDetails().getAddress())
                .size(company.getDetails().getSize())
                .founded(company.getDetails().getFounded())
                .linkedin(company.getDetails().getLinkedin())
                .instagram(company.getDetails().getInstagram())
                .twitter(company.getDetails().getTwitter())
                .facebook(company.getDetails().getFacebook())
                .build();

        return CompanyResponse.builder()
                .id(company.getId())
                .userId(company.getUser().getId())
                .name(company.getName())
                .description(company.getDescription())
                .location(company.getLocation())
                .details(details)
                .build();
    }
}