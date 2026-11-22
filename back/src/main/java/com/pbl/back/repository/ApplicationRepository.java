package com.pbl.back.repository;

import com.pbl.back.domain.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUserId(Long id);

    List<Application> findByJobPostingId(Long jobPostingId);

    boolean existsByUserIdAndJobPostingCompanyUserId(Long userId, Long companyOwnerUserId);
}
