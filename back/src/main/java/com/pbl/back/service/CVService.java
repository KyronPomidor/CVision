package com.pbl.back.service;

import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CVService {
    CVResponse create(Long userId, CVRequest request);

    CVResponse upload(Long userId, MultipartFile file);

    CVResponse getByUserId(Long userId);

    CVResponse getById(Long id);

    CVResponse update(Long id, CVRequest request);

    void delete(Long id);
}
