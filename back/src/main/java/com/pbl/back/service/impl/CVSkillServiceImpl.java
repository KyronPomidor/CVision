package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.CVSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;
import com.pbl.back.mapper.CVSkillMapper;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.CVSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.CVSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CVSkillServiceImpl implements CVSkillService {

    private final CVSkillRepository repository;
    private final CVRepository cvRepository;
    private final SkillRepository skillRepository;
    private final CVSkillMapper mapper;

    @Override
    public CVSkillResponse create(Long cvId, CVSkillRequest request) {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow();

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow();

        CVSkill cvSkill = mapper.toEntity(request);

        cvSkill.setCv(cv);
        cvSkill.setSkill(skill);

        return mapper.toResponse(repository.save(cvSkill));
    }

    @Override
    public CVSkillResponse getById(Long id) {
        CVSkill cvSkill = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(cvSkill);
    }

    @Override
    public List<CVSkillResponse> getByCvId(Long cvId) {
        return repository.findByCvId(cvId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<CVSkillResponse> getBySkillId(Long skillId) {
        return repository.findBySkillId(skillId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CVSkillResponse update(Long id, CVSkillRequest request) {
        CVSkill cvSkill = repository.findById(id)
                .orElseThrow();

        cvSkill.setConfidenceScore(request.getConfidenceScore());
        cvSkill.setYearsOfExperience(request.getYearsOfExperience());

        return mapper.toResponse(repository.save(cvSkill));
    }

    @Override
    public void delete(Long id) {

        CVSkill cvSkill = repository.findById(id)
                .orElseThrow();

        repository.delete(cvSkill);
    }
}