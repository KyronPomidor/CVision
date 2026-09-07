package com.pbl.back.mapper;

import com.pbl.back.domain.entity.CVSkill;
import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;

public interface CVSkillMapper {
    CVSkill toEntity(CVSkillRequest request);

    CVSkillResponse toResponse(CVSkill skill);
}
