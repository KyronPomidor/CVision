package com.pbl.back.repository;

import com.pbl.back.domain.entity.JobRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRecommendationRepository extends JpaRepository<JobRecommendation, Long> {
    List<JobRecommendation> findByUserId(Long userId);

    List<JobRecommendation> findByJobPostingId(Long jobPostingId);

    Optional<JobRecommendation> findFirstByUserIdAndJobPostingIdOrderByCreatedAtDesc(Long userId, Long jobPostingId);
}
