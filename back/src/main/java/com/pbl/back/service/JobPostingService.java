package com.pbl.back.service;

import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;

import java.util.List;

public interface JobPostingService {
    JobPostingResponse create(Long companyId, JobPostingRequest request);
    List<JobPostingResponse> getByCompanyId(Long companyId);
    JobPostingResponse getById(Long id);
    JobPostingResponse update(Long id, JobPostingRequest request);
    void delete(Long id);

}
