package com.pbl.back.service;

import com.pbl.back.dto.application.ApplicationRequest;
import com.pbl.back.dto.application.ApplicationResponse;
import java.util.List;

public interface ApplicationService {
    ApplicationResponse create(ApplicationRequest request);

    ApplicationResponse getMine();

    ApplicationResponse getById(Long id);

    ApplicationResponse update(Long id, ApplicationRequest request);

    void delete(Long id);

    List<ApplicationResponse> getApplicantsForPosting(Long postingId);
}
