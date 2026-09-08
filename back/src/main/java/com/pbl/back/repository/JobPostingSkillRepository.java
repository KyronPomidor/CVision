package com.pbl.back.repository;

import com.pbl.back.domain.entity.JobPostingSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostingSkillRepository extends JpaRepository<JobPostingSkill, Long> {

    List<JobPostingSkill> findByJobPostingId(Long jobPostingId);
}
