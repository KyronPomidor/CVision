package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.CVSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CVSkillMapper;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.CVSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.CVSkillService;
import com.pbl.back.service.CurrentUserService;
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
    private final CurrentUserService currentUserService;

    @Override
    public CVSkillResponse create(CVSkillRequest request) {
        CV cv = getCurrentCv();

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill with id '" + request.getSkillId() + "' not found."));

        CVSkill cvSkill = mapper.toEntity(request);

        cvSkill.setCv(cv);
        cvSkill.setSkill(skill);

        return mapper.toResponse(repository.save(cvSkill));
    }

    @Override
    public CVSkillResponse getById(Long id) {
        CVSkill cvSkill = getOwnedCvSkill(id);

        return mapper.toResponse(cvSkill);
    }

    @Override
    public List<CVSkillResponse> getMine() {
        CV cv = getCurrentCv();
        return repository.findByCvId(cv.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CVSkillResponse update(Long id, CVSkillRequest request) {
        CVSkill cvSkill = getOwnedCvSkill(id);

        cvSkill.setConfidenceScore(request.getConfidenceScore());
        cvSkill.setYearsOfExperience(request.getYearsOfExperience());

        return mapper.toResponse(repository.save(cvSkill));
    }

    @Override
    public void delete(Long id) {

        CVSkill cvSkill = getOwnedCvSkill(id);

        repository.delete(cvSkill);
    }

    private CV getCurrentCv() {
        return cvRepository.findByUserIdOrderByIdDesc(currentUserService.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CV not found"));
    }

    private CVSkill getOwnedCvSkill(Long id) {
        CVSkill cvSkill = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CVSkill with id '" + id + "' not found."));
        if (!cvSkill.getCv().getUser().getId().equals(currentUserService.getId())) {
            throw new ResourceNotFoundException("CVSkill with id '" + id + "' not found.");
        }
        return cvSkill;
    }
}