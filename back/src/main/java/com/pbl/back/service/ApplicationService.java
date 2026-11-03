package com.pbl.back.service;

import com.pbl.back.dto.application.ApplicationRequest;
import com.pbl.back.dto.application.ApplicationResponse;

public interface ApplicationService {
    ApplicationResponse create(ApplicationRequest request);

    ApplicationResponse getMine();

    ApplicationResponse getById(Long id);

    ApplicationResponse update(Long id, ApplicationRequest request);

    void delete(Long id);
}
