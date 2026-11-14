package com.pbl.back.service;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.domain.entity.CVSkill;
import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.ProfileSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.domain.entity.User;
import com.pbl.back.domain.enums.SkillLevel;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.CVSkillRepository;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.impl.CvSkillSyncServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CvSkillSyncServiceImplTest {

    @Mock
    private CVRepository cvRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private CVSkillRepository cvSkillRepository;

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @Mock
    private ProfileSkillRepository profileSkillRepository;

    private CvSkillSyncServiceImpl service;
    private CV cv;
    private CandidateProfile profile;

    @BeforeEach
    void setUp() {
        service = new CvSkillSyncServiceImpl(
                cvRepository,
                skillRepository,
                cvSkillRepository,
                candidateProfileRepository,
                profileSkillRepository);
        cv = CV.builder()
                .id(1L)
                .user(User.builder().id(7L).build())
                .build();
        profile = CandidateProfile.builder().id(9L).build();

        when(cvRepository.save(cv)).thenReturn(cv);
    }

    private void stubCandidateProfile() {
        when(candidateProfileRepository.findByUserId(7L)).thenReturn(Optional.of(profile));
    }

    private void stubCreatedSkill() {
        when(skillRepository.save(any(Skill.class))).thenAnswer(invocation -> {
            Skill skill = invocation.getArgument(0);
            skill.setId(11L);
            return skill;
        });
    }

    @Test
    void createsSkillAndLinksItToCvAndProfile() {
        stubCandidateProfile();
        stubCreatedSkill();
        service.saveCvWithSkills(cv, List.of("Java"));

        ArgumentCaptor<Skill> skillCaptor = ArgumentCaptor.forClass(Skill.class);
        ArgumentCaptor<CVSkill> cvSkillCaptor = ArgumentCaptor.forClass(CVSkill.class);
        ArgumentCaptor<ProfileSkill> profileSkillCaptor = ArgumentCaptor.forClass(ProfileSkill.class);
        verify(skillRepository).save(skillCaptor.capture());
        verify(cvSkillRepository).save(cvSkillCaptor.capture());
        verify(profileSkillRepository).save(profileSkillCaptor.capture());
        assertEquals("Java", skillCaptor.getValue().getName());
        assertSame(cv, cvSkillCaptor.getValue().getCv());
        assertSame(skillCaptor.getValue(), cvSkillCaptor.getValue().getSkill());
        assertNull(cvSkillCaptor.getValue().getConfidenceScore());
        assertNull(cvSkillCaptor.getValue().getYearsOfExperience());
        assertSame(profile, profileSkillCaptor.getValue().getProfile());
        assertSame(skillCaptor.getValue(), profileSkillCaptor.getValue().getSkill());
        assertEquals(SkillLevel.BEGINNER, profileSkillCaptor.getValue().getLevel());
        assertNull(profileSkillCaptor.getValue().getYearsOfExperience());
    }

    @Test
    void matchesExistingSkillWithoutCreatingDuplicate() {
        stubCandidateProfile();
        Skill existingSkill = Skill.builder().id(12L).name("Java").build();
        when(skillRepository.findFirstByNameIgnoreCase("java")).thenReturn(Optional.of(existingSkill));

        service.saveCvWithSkills(cv, List.of("java"));

        verify(skillRepository, never()).save(any(Skill.class));
        verify(cvSkillRepository).save(any(CVSkill.class));
        verify(profileSkillRepository).save(any(ProfileSkill.class));
    }

    @Test
    void collapsesCaseAndWhitespaceDuplicatesKeepingFirstSpelling() {
        stubCandidateProfile();
        stubCreatedSkill();
        service.saveCvWithSkills(cv, List.of("Java", "java", " JAVA "));

        verify(skillRepository).findFirstByNameIgnoreCase("Java");
        verify(skillRepository).save(any(Skill.class));
        verify(cvSkillRepository).save(any(CVSkill.class));
        verify(profileSkillRepository).save(any(ProfileSkill.class));
    }

    @Test
    void skipsBlankNullAndOverlongNames() {
        stubCandidateProfile();
        stubCreatedSkill();
        service.saveCvWithSkills(cv, Arrays.asList(" Python ", "   ", null, "x".repeat(101)));

        ArgumentCaptor<Skill> skillCaptor = ArgumentCaptor.forClass(Skill.class);
        verify(skillRepository).save(skillCaptor.capture());
        assertEquals("Python", skillCaptor.getValue().getName());
        verify(cvSkillRepository).save(any(CVSkill.class));
        verify(profileSkillRepository).save(any(ProfileSkill.class));
    }

    @Test
    void doesNotTouchAnExistingProfileSkill() {
        stubCandidateProfile();
        Skill existingSkill = Skill.builder().id(12L).name("Java").build();
        when(skillRepository.findFirstByNameIgnoreCase("Java")).thenReturn(Optional.of(existingSkill));
        when(profileSkillRepository.existsByProfileIdAndSkillId(9L, 12L)).thenReturn(true);

        service.saveCvWithSkills(cv, List.of("Java"));

        verify(cvSkillRepository).save(any(CVSkill.class));
        verify(profileSkillRepository).existsByProfileIdAndSkillId(9L, 12L);
        verify(profileSkillRepository, never()).save(any(ProfileSkill.class));
    }

    @Test
    void savesCvAndCvSkillWhenUserHasNoCandidateProfile() {
        when(candidateProfileRepository.findByUserId(7L)).thenReturn(Optional.empty());
        stubCreatedSkill();

        service.saveCvWithSkills(cv, List.of("Java"));

        verify(cvRepository).save(cv);
        verify(skillRepository).save(any(Skill.class));
        verify(cvSkillRepository).save(any(CVSkill.class));
        verify(profileSkillRepository, never()).save(any(ProfileSkill.class));
    }

    @Test
    void savesCvWithoutProcessingSkillsWhenListIsEmpty() {
        CV savedCv = service.saveCvWithSkills(cv, List.of());

        assertSame(cv, savedCv);
        verify(cvRepository).save(cv);
        verifyNoInteractions(skillRepository, cvSkillRepository,
                candidateProfileRepository, profileSkillRepository);
    }
}
