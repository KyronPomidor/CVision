package com.pbl.back.repository;

import com.pbl.back.domain.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CVRepository extends JpaRepository<CV, Long> {
    List<CV> findByUserIdOrderByIdDesc(Long userId);
}
