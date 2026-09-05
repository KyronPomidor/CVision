package com.pbl.back.service;

import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;

public interface CompanyService {
    CompanyResponse create(Long userId, CompanyRequest request);

    CompanyResponse getByUserId(Long userId);

    CompanyResponse getById(Long id);

    CompanyResponse update(Long id, CompanyRequest request);

    void delete(Long id);
}
