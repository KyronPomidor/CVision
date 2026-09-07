package com.pbl.back.repository;

import com.pbl.back.domain.entity.CVSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CVSkillRepository extends JpaRepository<CVSkill, Long> {

    List<CVSkill> findByCvId(Long cvId);

    List<CVSkill> findBySkillId(Long skillId);
}