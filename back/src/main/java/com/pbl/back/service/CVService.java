package com.pbl.back.service;

import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;

public interface CVService {
    CVResponse create(Long userId, CVRequest request);

    CVResponse getByUserId(Long userId);

    CVResponse getById(Long id);

    CVResponse update(Long id, CVRequest request);

    void delete(Long id);
}
