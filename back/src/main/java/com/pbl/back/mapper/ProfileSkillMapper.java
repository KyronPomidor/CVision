package com.pbl.back.mapper;

import com.pbl.back.domain.entity.ProfileSkill;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import org.springframework.stereotype.Component;

@Component
public interface ProfileSkillMapper {
    ProfileSkillResponse toResponse(ProfileSkill profileSkill);
}
