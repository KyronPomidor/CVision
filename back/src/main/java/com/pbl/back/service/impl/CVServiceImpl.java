package com.pbl.back.service.impl;

import com.pbl.back.dto.skill.ExtractedSkill;

import com.pbl.back.service.*;
import org.springframework.security.access.AccessDeniedException;
import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.cv.CVInformationRequest;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.exception.FileStorageException;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CVMapper;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CVServiceImpl implements CVService {

    private final CVRepository repository;
    private final TextExtractor textExtractor;
    private final CVMapper mapper;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final PerplexityService perplexityService;
    private final CvSkillSyncService cvSkillSyncService;
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
    @Transactional(rollbackFor = Exception.class)
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
            String cvText = textExtractor.extract(file).trim();

            String skillsJson = perplexityService.extractSkills(file);
            List<ExtractedSkill> extractedSkills = objectMapper.readValue(
                    skillsJson,
                    new TypeReference<List<ExtractedSkill>>() {}
            );

            User lockedUser = lockUser(user.getId());
            CV cv = repository.findByUserIdOrderByIdDesc(userId)
                    .stream()
                    .findFirst()
                    .orElseGet(() -> CV.builder().user(lockedUser).build());
            String previousPath = cv.getFilePath();
            registerFileCleanup(target, previousPath);

            cv.setFileName(originalFileName);
            cv.setFilePath(target.toString());
            cv.setExtractedText(cvText);
            cv.setUploadedAt(LocalDateTime.now());

            return mapper.toResponse(cvSkillSyncService.replaceExtractedSkills(cv, extractedSkills));
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
        CV cv = repository.findByUserIdOrderByIdDesc(userId)
                    .stream()
                    .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CV for user with id '" + userId + "' not found."));

        return mapper.toResponse(cv);
    }

    @Override
    public CVResponse getById(Long id) {
        return mapper.toResponse(getOwnedCv(id));
    }

    @Override
    public CVResponse update(Long id, CVRequest request) {
        CV cv = getOwnedCv(id);

        cv.setFileName(request.getFileName());
        cv.setFilePath(request.getFilePath());
        cv.setExtractedText(request.getExtractedText());
        cv.setUploadedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(cv));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CVResponse updateInformation(Long userId, CVInformationRequest request) {
        User user = lockUser(userId);
        CV cv = repository.findByUserIdOrderByIdDesc(userId)
                    .stream()
                    .findFirst()
                .orElseGet(() -> CV.builder()
                        .user(user)
                        .fileName("")
                        .filePath("")
                        .build());
        try {
            cv.setLanguages(objectMapper.writeValueAsString(request.getLanguages()));
            cv.setExperience(objectMapper.writeValueAsString(request.getExperience()));
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Could not serialize CV information", ex);
        }
        cv.setObjective(request.getObjective() == null ? null : request.getObjective().trim());
        cv.setAdditionalInfo(request.getAdditionalInfo() == null ? null : request.getAdditionalInfo().trim());

        CV saved = request.getSkillExperience() == null
                ? cvSkillSyncService.replaceCvWithSkills(cv, request.getSkills())
                : cvSkillSyncService.replaceExtractedSkills(cv, request.getSkillExperience());
        return mapper.toResponse(saved);
    }

    private User lockUser(Long userId) {
        return userRepository.findWithLockById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void registerFileCleanup(Path uploadedFile, String previousPath) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status != STATUS_COMMITTED) {
                    deleteStoredFile(uploadedFile);
                } else if (previousPath != null && !previousPath.isBlank()) {
                    deleteStoredFile(Path.of(previousPath).toAbsolutePath().normalize());
                }
            }
        });
    }

    private void deleteStoredFile(Path file) {
        Path directory = Path.of(cvStorageDirectory).toAbsolutePath().normalize();
        if (!file.startsWith(directory)) return;
        try {
            Files.deleteIfExists(file);
        } catch (IOException ex) {
            log.warn("Could not remove stored CV file '{}'", file, ex);
        }
    }

    @Override
    public void delete(Long id) {
        repository.delete(getOwnedCv(id));
    }

    private CV getOwnedCv(Long id) {
        CV cv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CV not found"));

        if (!cv.getUser().getId().equals(currentUserService.getId())) {
            throw new ResourceNotFoundException("CV not found");
        }

        return cv;
    }
}
