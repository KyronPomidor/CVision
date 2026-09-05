package com.pbl.back.repository;

import com.pbl.back.domain.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
