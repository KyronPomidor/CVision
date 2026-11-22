package com.pbl.back.service;

import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;

import java.util.List;

public interface CVSkillService {
    CVSkillResponse create(CVSkillRequest request);

    CVSkillResponse getById(Long id);

    List<CVSkillResponse> getMine();

    CVSkillResponse update(Long id, CVSkillRequest request);

    void delete(Long id);
}
