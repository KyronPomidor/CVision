package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CVMapper;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CVService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVRepository repository;
    private final CVMapper mapper;
    private final UserRepository userRepository;

    @Override
    public CVResponse create(Long userId, CVRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        CV cv = mapper.toEntity(request);

        cv.setUser(user);

        return mapper.toResponse(repository.save(cv));
    }

    @Override
    public CVResponse getByUserId(Long userId) {
        CV cv = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("CV for user with id '" + userId + "' not found."));

        return mapper.toResponse(cv);
    }

    @Override
    public CVResponse getById(Long id) {
        CV cv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CV with id '" + id + "' not found."));

        return mapper.toResponse(cv);
    }

    @Override
    public CVResponse update(Long id, CVRequest request) {
        CV cv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CV with id '" + id + "' not found."));

        cv.setFileName(request.getFileName());
        cv.setFilePath(request.getFilePath());
        cv.setExtractedText(request.getExtractedText());
        cv.setUploadedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(cv));
    }

    @Override
    public void delete(Long id) {
        CV cv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CV with id '" + id + "' not found."));

        repository.delete(cv);
    }
}
