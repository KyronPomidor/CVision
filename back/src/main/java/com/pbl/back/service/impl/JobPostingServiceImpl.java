package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.mapper.JobPostingMapper;
import com.pbl.back.repository.CompanyRepository;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository repository;
    private final JobPostingMapper mapper;
    private final CompanyRepository companyRepository;

    @Override
    public JobPostingResponse create(Long companyId, JobPostingRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow();

        JobPosting posting = mapper.toEntity(request);

        posting.setCompany(company);

        JobPosting savedPosting = repository.save(posting);

        return mapper.toResponse(savedPosting);
    }

    @Override
    public JobPostingResponse getByCompanyId(Long companyId) {
        JobPosting posting = repository.findByCompanyId(companyId)
                .orElseThrow();
        return mapper.toResponse(posting);
    }

    @Override
    public JobPostingResponse getById(Long id) {
        JobPosting posting = repository.findById(id)
                .orElseThrow();
        return mapper.toResponse(posting);
    }

    @Override
    public JobPostingResponse update(Long id, JobPostingRequest request) {
        JobPosting posting = repository.findById(id)
                .orElseThrow();

        posting.setTitle(request.getTitle());
        posting.setDescription(request.getDescription());
        posting.setLocation(request.getLocation());
        posting.setEmploymentType(request.getEmploymentType());
        posting.setSalary(request.getSalary());

        return mapper.toResponse(repository.save(posting));
    }

    @Override
    public void delete(Long id) {
        JobPosting posting = repository.findById(id)
                .orElseThrow();

        repository.delete(posting);
    }
}
