package com.pbl.back.service;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.User;
import com.pbl.back.domain.enums.Role;
import com.pbl.back.dto.cv.CVExperience;
import com.pbl.back.dto.cv.CVInformationRequest;
import com.pbl.back.dto.cv.CVLanguage;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.CVSkillRepository;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:cv-information-test;DB_CLOSE_DELAY=-1",
        "app.storage.cv-directory=target/cv-information-test-files"
})
class CVInformationServiceTest {

    @Autowired private CVService service;
    @Autowired private CVRepository cvRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CandidateProfileRepository profileRepository;
    @Autowired private CVSkillRepository cvSkillRepository;
    @Autowired private ProfileSkillRepository profileSkillRepository;
    @MockitoBean private PerplexityService extraction;

    private User user;
    private CandidateProfile profile;
    private String marker;

    @BeforeEach
    void setUp() {
        marker = UUID.randomUUID().toString();
        user = userRepository.save(User.builder()
                .accountName("CV test")
                .email(marker + "@example.test")
                .password("test-only")
                .role(Role.EMPLOYEE)
                .createdAt(LocalDateTime.now())
                .build());
        profile = profileRepository.save(CandidateProfile.builder()
                .user(user).name("CV test").build());
    }

    @AfterEach
    void cleanFiles() throws Exception {
        Path directory = Path.of("target/cv-information-test-files");
        if (!Files.exists(directory)) return;
        try (var files = Files.list(directory)) {
            for (Path file : files.filter(path -> path.getFileName().toString().contains(marker)).toList()) {
                Files.deleteIfExists(file);
            }
        }
    }

    private CVInformationRequest information() {
        CVInformationRequest request = new CVInformationRequest();
        request.setSkills(List.of("Java"));
        request.setLanguages(List.of(new CVLanguage("English", "B2")));
        request.setObjective("Build web applications");
        request.setAdditionalInfo("Available part-time");
        request.setExperience(List.of(new CVExperience(
                1L, "Developer", "Example", "Chisinau",
                LocalDate.of(2025, 3, 1), null, "Built APIs")));
        return request;
    }

    private MockMultipartFile file(String suffix) {
        return new MockMultipartFile("file", marker + suffix + ".txt",
                "text/plain", "Java developer".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void savesInformationWithoutAFileAndKeepsTheSameRowOnEdit() {
        CVResponse saved = service.updateInformation(user.getId(), information());
        assertEquals("", saved.getFilePath());
        assertEquals(List.of(new CVLanguage("English", "B2")), saved.getLanguages());
        assertEquals(LocalDate.of(2025, 3, 1), saved.getExperience().getFirst().startDate());
        assertNull(saved.getExperience().getFirst().endDate());

        CVInformationRequest changed = information();
        changed.setObjective("Updated objective");
        changed.setSkills(List.of("Python", "python"));
        CVResponse updated = service.updateInformation(user.getId(), changed);
        assertEquals(saved.getId(), updated.getId());
        assertEquals(List.of("Python"), updated.getSkills());
        assertEquals("Updated objective", service.getByUserId(user.getId()).getObjective());
        assertEquals(List.of("Python"), cvSkillRepository.findByCvId(saved.getId()).stream()
                .map(link -> link.getSkill().getName()).toList());
        assertEquals(List.of("Python"), profileSkillRepository.findByProfileId(profile.getId()).stream()
                .map(link -> link.getSkill().getName()).toList());

        changed.setSkills(List.of());
        changed.setLanguages(List.of());
        changed.setExperience(List.of());
        changed.setObjective("");
        changed.setAdditionalInfo("");
        CVResponse cleared = service.updateInformation(user.getId(), changed);
        assertTrue(cleared.getSkills().isEmpty());
        assertTrue(cleared.getLanguages().isEmpty());
        assertTrue(cleared.getExperience().isEmpty());
        assertTrue(cvSkillRepository.findByCvId(saved.getId()).isEmpty());
        assertTrue(profileSkillRepository.findByProfileId(profile.getId()).isEmpty());
    }

    @Test
    void uploadUpdatesTheManualCvAndReplacesTheFileOnRepeatedUpload() {
        CVResponse manual = service.updateInformation(user.getId(), information());
        when(extraction.extractSkills(any())).thenReturn("[{\"name\":\"Python\",\"yearsOfExperience\":3}]");

        CVResponse uploaded = service.upload(user.getId(), file("-first"));
        assertEquals(manual.getId(), uploaded.getId());
        assertEquals(manual.getObjective(), uploaded.getObjective());
        assertEquals(manual.getLanguages(), uploaded.getLanguages());
        assertEquals(manual.getExperience(), uploaded.getExperience());
        assertEquals(manual.getAdditionalInfo(), uploaded.getAdditionalInfo());
        assertEquals(List.of("Python"), uploaded.getSkills());
        assertEquals(3, uploaded.getSkillExperience().getFirst().yearsOfExperience());
        assertEquals(3, cvSkillRepository.findByCvId(uploaded.getId()).getFirst().getYearsOfExperience());
        assertEquals("Java developer", uploaded.getExtractedText());
        assertTrue(Files.exists(Path.of(uploaded.getFilePath())));

        when(extraction.extractSkills(any())).thenReturn("[{\"name\":\"Python\",\"yearsOfExperience\":null}]");
        CVResponse replaced = service.upload(user.getId(), file("-second"));
        assertNull(service.getByUserId(user.getId()).getSkillExperience().getFirst().yearsOfExperience());
        assertNull(profileSkillRepository.findByProfileId(profile.getId()).getFirst().getYearsOfExperience());
        CVInformationRequest edited = information();
        edited.setSkillExperience(List.of(new com.pbl.back.dto.skill.ExtractedSkill("Python", 0)));
        assertEquals(0, service.updateInformation(user.getId(), edited).getSkillExperience().getFirst().yearsOfExperience());
        assertEquals(manual.getId(), replaced.getId());
        assertFalse(Files.exists(Path.of(uploaded.getFilePath())));
        assertTrue(Files.exists(Path.of(replaced.getFilePath())));
        assertEquals(1, cvRepository.findAll().stream()
                .filter(cv -> cv.getUser().getId().equals(user.getId())).count());
    }

    @Test
    void extractionFailurePreservesThePreviousRowAndFile() throws Exception {
        when(extraction.extractSkills(any())).thenReturn("[{\"name\":\"Java\",\"yearsOfExperience\":3}]");
        CVResponse original = service.upload(user.getId(), file("-original"));
        when(extraction.extractSkills(any())).thenThrow(new IllegalStateException("Extraction unavailable"));

        assertThrows(IllegalStateException.class, () -> service.upload(user.getId(), file("-failed")));
        CVResponse unchanged = service.getByUserId(user.getId());
        assertEquals(original.getId(), unchanged.getId());
        assertEquals(original.getFilePath(), unchanged.getFilePath());
        assertEquals(original.getSkills(), unchanged.getSkills());
        assertTrue(Files.exists(Path.of(original.getFilePath())));
        try (var files = Files.list(Path.of("target/cv-information-test-files"))) {
            assertFalse(files.anyMatch(path -> path.getFileName().toString().contains(marker + "-failed")));
        }
    }

    @Test
    void concurrentFirstSavesCreateOnlyOneCv() throws Exception {
        CountDownLatch start = new CountDownLatch(1);
        try (var executor = Executors.newFixedThreadPool(2)) {
            var first = executor.submit(() -> {
                start.await();
                return service.updateInformation(user.getId(), information());
            });
            var second = executor.submit(() -> {
                start.await();
                return service.updateInformation(user.getId(), information());
            });
            start.countDown();
            assertEquals(first.get(10, TimeUnit.SECONDS).getId(), second.get(10, TimeUnit.SECONDS).getId());
        }
        assertEquals(1, cvRepository.findAll().stream()
                .filter(cv -> cv.getUser().getId().equals(user.getId())).count());
    }
}
