package com.pbl.back.repository;

import com.pbl.back.domain.entity.JobPostingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingImageRepository extends JpaRepository<JobPostingImage, Long> {
    List<JobPostingImage> findByJobPostingIdOrderBySortOrderAsc(Long jobPostingId);

    Optional<JobPostingImage> findByIdAndJobPostingId(Long id, Long jobPostingId);

    long countByJobPostingId(Long jobPostingId);
}
