package com.pbl.back.service;

import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;

import java.util.List;

public interface CVSkillService {
    CVSkillResponse create(Long cvId, CVSkillRequest request);

    CVSkillResponse getById(Long id);

    List<CVSkillResponse> getByCvId(Long cvId);

    List<CVSkillResponse> getBySkillId(Long skillId);

    CVSkillResponse update(Long id, CVSkillRequest request);

    void delete(Long id);
}
