package com.pbl.back.service;

import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    CompanyResponse create(CompanyRequest request);

    CompanyResponse getMe();

    CompanyResponse getById(Long id);

    CompanyResponse updateMe(CompanyRequest request);

    void deleteMe();

    CompanyResponse uploadPicture(MultipartFile file);

    ResponseEntity<Resource> getPicture(Long id);

    void deletePicture();
}
