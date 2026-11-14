package com.pbl.back.service;

import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobPostingService {
    JobPostingResponse create(Long companyId, JobPostingRequest request);
    List<JobPostingResponse> getByCompanyId(Long companyId);
    List<JobPostingResponse> getMatchesForUser(Long userId);
    JobPostingResponse getById(Long id);
    JobPostingResponse update(Long id, JobPostingRequest request);
    void delete(Long id);
    JobPostingResponse uploadImages(Long postingId, List<MultipartFile> files);
    ResponseEntity<Resource> getImage(Long postingId, Long imageId);
    void deleteImage(Long postingId, Long imageId);

}
