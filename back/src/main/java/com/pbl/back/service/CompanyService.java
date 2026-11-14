package com.pbl.back.service;

import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    CompanyResponse create(Long userId, CompanyRequest request);

    CompanyResponse getByUserId(Long userId);

    CompanyResponse getById(Long id);

    CompanyResponse update(Long id, CompanyRequest request);

    void delete(Long id);

    CompanyResponse uploadPicture(MultipartFile file);

    ResponseEntity<Resource> getPicture(Long id);

    void deletePicture();
}
