package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ApplicationRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.service.CandidateProfileService;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.ImageStorageService;
import com.pbl.back.service.ImageStorageService.StoredImage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileRepository repository;
    private final CandidateProfileMapper mapper;
    private final ApplicationRepository applicationRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final ProfileSkillMapper profileSkillMapper;
    private final CurrentUserService currentUserService;
    private final ImageStorageService imageStorageService;

    public CandidateProfileResponse create(CandidateProfileRequest request) {
        CandidateProfile profile = mapper.toEntity(request);
        profile.setUser(currentUserService.get());

        CandidateProfile savedProfile = repository.save(profile);

        return mapper.toResponse(savedProfile, List.of());
    }

    @Override
    public CandidateProfileResponse getMe() {
        Long userId = currentUserService.getId();
        CandidateProfile profile = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("" +
                        "CandidateProfile for user with id '" + userId + "' not found."));

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(profile, skills);
    }

    @Override
    public CandidateProfileResponse getById(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));
        Long currentUserId = currentUserService.getId();
        Long candidateUserId = profile.getUser() == null ? null : profile.getUser().getId();
        if (!currentUserId.equals(candidateUserId)) {
            boolean employer = org.springframework.security.core.context.SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_EMPLOYER"));
            if (!employer || candidateUserId == null
                    || !applicationRepository.existsByUserIdAndJobPostingCompanyUserId(
                    candidateUserId, currentUserId)) {
                if (!employer) {
                    throw new ResourceNotFoundException("CandidateProfile with id '" + id + "' not found.");
                }
                throw new AccessDeniedException("You are not authorized to review this candidate.");
            }
        }

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(profile, skills);
    }

    @Override
    public CandidateProfileResponse updateMe(CandidateProfileRequest request) {
        CandidateProfile profile = repository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your profile first."));

        profile.setName(request.getName());
        profile.setLocation(request.getLocation());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setDescription(request.getDescription());

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(repository.save(profile), skills);
    }

    @Override
    public void deleteMe() {
        CandidateProfile profile = repository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your profile first."));

        repository.delete(profile);
        imageStorageService.deleteDirectoryQuietly("profiles/" + profile.getId());
    }

    @Override
    public CandidateProfileResponse uploadPicture(MultipartFile file) {
        Long userId = currentUserService.getId();
        CandidateProfile profile = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Create your profile first."));
        String oldPicturePath = profile.getPicturePath();
        StoredImage newImage = imageStorageService.store(file, "profiles/" + profile.getId());

        profile.setPicturePath(newImage.relativePath());
        profile.setPictureContentType(newImage.contentType());
        try {
            repository.save(profile);
        } catch (RuntimeException ex) {
            imageStorageService.deleteQuietly(newImage.relativePath());
            throw ex;
        }

        if (oldPicturePath != null) {
            imageStorageService.deleteQuietly(oldPicturePath);
        }
        return getMe();
    }

    @Override
    public ResponseEntity<Resource> getPicture(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));
        if (profile.getUser() == null
                || !profile.getUser().getId().equals(currentUserService.getId())) {
            throw new ResourceNotFoundException("CandidateProfile with id '" + id + "' not found.");
        }
        if (profile.getPicturePath() == null) {
            throw new ResourceNotFoundException("Profile picture not found.");
        }
        return inlineImage(profile.getPicturePath(), profile.getPictureContentType());
    }

    @Override
    public void deletePicture() {
        CandidateProfile profile = repository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your profile first."));
        String oldPicturePath = profile.getPicturePath();
        profile.setPicturePath(null);
        profile.setPictureContentType(null);
        repository.save(profile);
        if (oldPicturePath != null) {
            imageStorageService.deleteQuietly(oldPicturePath);
        }
    }

    private ResponseEntity<Resource> inlineImage(String filePath, String contentType) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline().build().toString())
                .cacheControl(CacheControl.noCache().cachePrivate())
                .body(imageStorageService.load(filePath));
    }
}