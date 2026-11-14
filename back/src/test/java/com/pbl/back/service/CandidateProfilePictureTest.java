package com.pbl.back.service;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.User;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.impl.CandidateProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CandidateProfilePictureTest {

    @TempDir
    Path baseDirectory;

    @Test
    void reuploadReplacesPictureAndDeletesOldFile() throws Exception {
        CandidateProfileRepository profiles = mock(CandidateProfileRepository.class);
        ProfileSkillRepository profileSkills = mock(ProfileSkillRepository.class);
        CurrentUserService currentUserService = mock(CurrentUserService.class);
        CandidateProfileMapper mapper = new com.pbl.back.mapper.impl.CandidateProfileMapperImpl();
        ProfileSkillMapper profileSkillMapper = mock(ProfileSkillMapper.class);
        CandidateProfile profile = CandidateProfile.builder()
                .id(4L)
                .user(User.builder().id(7L).build())
                .name("Candidate")
                .picturePath("profiles/4/old.png")
                .pictureContentType("image/png")
                .build();
        Path profileDirectory = baseDirectory.resolve("profiles").resolve("4");
        Files.createDirectories(profileDirectory);
        Path oldFile = profileDirectory.resolve("old.png");
        Files.writeString(oldFile, "old image");

        when(currentUserService.getId()).thenReturn(7L);
        when(profiles.findByUserId(7L)).thenReturn(Optional.of(profile));
        when(profiles.save(any(CandidateProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(profileSkills.findByProfileId(4L)).thenReturn(List.of());

        CandidateProfileServiceImpl service = new CandidateProfileServiceImpl(
                profiles,
                mapper,
                mock(UserRepository.class),
                profileSkills,
                profileSkillMapper,
                currentUserService,
                new ImageStorageService(baseDirectory.toString()));

        var response = service.uploadPicture(new MockMultipartFile(
                "file",
                "new.png",
                "image/png",
                new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}));

        assertFalse(Files.exists(oldFile));
        assertNotNull(profile.getPicturePath());
        assertNotNull(response.getPictureUrl());
        verify(profiles).save(profile);
    }
}
