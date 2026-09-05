package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.ProfileSkill;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import org.springframework.stereotype.Component;

@Component
public class ProfileSkillMapperImpl {

    public ProfileSkillResponse toResponse(ProfileSkill profileSkill) {
        return ProfileSkillResponse.builder()
                .id(profileSkill.getId())
                .skillId(profileSkill.getSkill().getId())
                .skillName(profileSkill.getSkill().getName())
                .level(profileSkill.getLevel())
                .yearsOfExperience(profileSkill.getYearsOfExperience())
                .build();
    }
}