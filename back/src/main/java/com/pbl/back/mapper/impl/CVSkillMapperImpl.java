package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.CVSkill;
import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;
import com.pbl.back.mapper.CVSkillMapper;
import org.springframework.stereotype.Component;

@Component
public class CVSkillMapperImpl implements CVSkillMapper {

    public CVSkill toEntity(CVSkillRequest request) {
        return CVSkill.builder()
                .confidenceScore(request.getConfidenceScore())
                .yearsOfExperience(request.getYearsOfExperience())
                .build();
    }

    public CVSkillResponse toResponse(CVSkill cvSkill) {
        return CVSkillResponse.builder()
                .id(cvSkill.getId())
                .cvId(cvSkill.getCv().getId())
                .skillId(cvSkill.getSkill().getId())
                .skillName(cvSkill.getSkill().getName())
                .confidenceScore(cvSkill.getConfidenceScore())
                .yearsOfExperience(cvSkill.getYearsOfExperience())
                .build();
    }
}