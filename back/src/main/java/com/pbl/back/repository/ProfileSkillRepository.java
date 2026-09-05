package com.pbl.back.repository;

import com.pbl.back.domain.entity.ProfileSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileSkillRepository extends JpaRepository<ProfileSkill, Long> {

    List<ProfileSkill> findByProfileId(Long profileId);
}