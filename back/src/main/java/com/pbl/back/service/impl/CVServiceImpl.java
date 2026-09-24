package com.pbl.back.service.impl;

import org.springframework.security.access.AccessDeniedException;
import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.exception.FileStorageException;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CVMapper;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CVService;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.PerplexityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVRepository repository;
    private final CVMapper mapper;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final PerplexityService perplexityService;
    private final ObjectMapper objectMapper;

    @Value("${app.storage.cv-directory:uploads/cvs}")
    private String cvStorageDirectory;

    @Override
    public CVResponse create(Long userId, CVRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        CV cv = mapper.toEntity(request);

        cv.setUser(user);

        return mapper.toResponse(repository.save(cv));
    }

    @Override
    public CVResponse upload(Long userId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("A CV file is required.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        String originalFileName = StringUtils.cleanPath(
                file.getOriginalFilename() == null ? "cv" : file.getOriginalFilename());
        if (originalFileName.isBlank() || originalFileName.contains("..")) {
            throw new FileStorageException("The CV file name is invalid.");
        }

        Path storageDirectory = Path.of(cvStorageDirectory).toAbsolutePath().normalize();
        String storedFileName = UUID.randomUUID() + "_" + originalFileName;
        Path target = storageDirectory.resolve(storedFileName).normalize();

        if (!target.startsWith(storageDirectory)) {
            throw new FileStorageException("The CV file name is invalid.");
        }

        try {
            Files.createDirectories(storageDirectory);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not save the CV file.", ex);
        }

        try {
            String skillsJson = perplexityService.extractSkills(file);
            objectMapper.readValue(skillsJson, new TypeReference<java.util.List<String>>() {});

            CV cv = CV.builder()
                    .user(user)
                    .fileName(originalFileName)
                    .filePath(target.toString())
                    .skills(skillsJson)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            return mapper.toResponse(repository.save(cv));
        } catch (JsonProcessingException ex) {
            throw new FileStorageException("Gemini returned invalid skills JSON.", ex);
        } catch (RuntimeException ex) {
            try {
                Files.deleteIfExists(target);
            } catch (IOException cleanupException) {
                ex.addSuppressed(cleanupException);
            }
            throw ex;
        }
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

    private CV getOwnedCv(Long id) {
        CV cv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CV not found"));

        if (!cv.getUser().getId().equals(currentUserService.getId())) {
            throw new AccessDeniedException("You do not own this CV");
        }

        return cv;
    }
}
