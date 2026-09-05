package com.pbl.back.service;

import com.pbl.back.dto.skill.SkillRequest;
import com.pbl.back.dto.skill.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse create(SkillRequest request);

    SkillResponse getById(Long id);

    SkillResponse update(Long id, SkillRequest request);

    void delete(Long id);

    List<SkillResponse> getAll();
}
