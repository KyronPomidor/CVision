package com.pbl.back.mapper;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;

public interface CompanyMapper {
    Company toEntity(CompanyRequest request);

    CompanyResponse toResponse(Company company);
}
