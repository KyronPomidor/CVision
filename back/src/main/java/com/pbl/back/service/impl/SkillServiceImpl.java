package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.skill.SkillRequest;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.mapper.SkillMapper;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;
    private final SkillMapper mapper;

    @Override
    public SkillResponse create(SkillRequest request) {
        Skill skill = mapper.toEntity(request);

        return mapper.toResponse(repository.save(skill));
    }

    @Override
    public SkillResponse getById(Long id) {
        Skill skill = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(skill);
    }

    @Override
    public List<SkillResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SkillResponse update(Long id, SkillRequest request) {
        Skill skill = repository.findById(id)
                .orElseThrow();

        skill.setName(request.getName());

        return mapper.toResponse(repository.save(skill));
    }

    @Override
    public void delete(Long id) {
        Skill skill = repository.findById(id)
                .orElseThrow();

        repository.delete(skill);
    }
}
