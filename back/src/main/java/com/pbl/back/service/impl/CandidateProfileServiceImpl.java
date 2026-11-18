package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.dto.candidateprofile.CurrentCandidateProfileResponse;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final ProfileSkillMapper profileSkillMapper;
    private final CurrentUserService currentUserService;

    @Override
    public CurrentCandidateProfileResponse getCurrentProfile() {
        User user = currentUserService.get();

        var profile = repository.findByUserId(user.getId());

        return CurrentCandidateProfileResponse.builder()
                .name(profile.map(CandidateProfile::getName).orElse(user.getAccountName()))
                .email(user.getEmail())
                .phone(profile.map(CandidateProfile::getPhone).orElse(null))
                .location(profile.map(CandidateProfile::getLocation).orElse(null))
                .description(profile.map(CandidateProfile::getDescription).orElse(null))
                .build();
    }
    private final ImageStorageService imageStorageService;

    // TODO: get user without client id
    public CandidateProfileResponse create(Long userId, CandidateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        CandidateProfile profile = mapper.toEntity(request);

        profile.setUser(user);

        CandidateProfile savedProfile = repository.save(profile);

        return mapper.toResponse(savedProfile, List.of());
    }

    @Override
    public CandidateProfileResponse getByUserId(Long userId) {

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

        List<ProfileSkillResponse> skills =
                profileSkillRepository.findByProfileId(profile.getId())
                        .stream()
                        .map(profileSkillMapper::toResponse)
                        .toList();

        return mapper.toResponse(profile, skills);
    }

    @Override
    public CandidateProfileResponse update(Long id, CandidateProfileRequest request) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));

        profile.setName(request.getName());
        profile.setPhone(request.getPhone());
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
    public void delete(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));

        repository.delete(profile);
        imageStorageService.deleteDirectoryQuietly("profiles/" + id);
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
        return getByUserId(userId);
    }

    @Override
    public ResponseEntity<Resource> getPicture(Long id) {
        CandidateProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CandidateProfile with id '" + id + "' not found."));
        if (profile.getUser() == null
                || !profile.getUser().getId().equals(currentUserService.getId())) {
            throw new AccessDeniedException("You do not own this candidate profile.");
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