package com.pbl.back.repository;

import com.pbl.back.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository  extends JpaRepository<Company, Long> {
    Optional<Company> findByUserId(Long userId);
}
