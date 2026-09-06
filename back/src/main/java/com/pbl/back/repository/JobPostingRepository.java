package com.pbl.back.repository;

import com.pbl.back.domain.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    Optional<JobPosting> findByCompanyId(Long companyId);
}
