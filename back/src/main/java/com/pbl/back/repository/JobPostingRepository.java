package com.pbl.back.repository;

import com.pbl.back.domain.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.pbl.back.domain.enums.JobStatus;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByCompanyId(Long companyId);

    List<JobPosting> findByStatus(JobStatus status);
}
