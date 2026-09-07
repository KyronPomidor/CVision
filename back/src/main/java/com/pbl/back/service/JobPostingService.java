package com.pbl.back.service;

import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;

public interface JobPostingService {
    public JobPostingResponse create(Long companyId, JobPostingRequest request);
    public JobPostingResponse getByCompanyId(Long companyId);
    public JobPostingResponse getById(Long id);
    public JobPostingResponse update(Long id, JobPostingRequest request);
    public void delete(Long id);

}
