package com.pbl.back.service;

import com.pbl.back.domain.entity.*;
import com.pbl.back.dto.cvskill.CVSkillRequest;
import com.pbl.back.dto.cvskill.CVSkillResponse;
import com.pbl.back.dto.jobrecommendation.JobRecommendationRequest;
import com.pbl.back.dto.jobrecommendation.JobRecommendationResponse;
import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CVSkillMapper;
import com.pbl.back.mapper.JobRecommendationMapper;
import com.pbl.back.mapper.ProfileSkillMapper;
import com.pbl.back.repository.*;
import com.pbl.back.service.impl.CVSkillServiceImpl;
import com.pbl.back.service.impl.JobRecommendationServiceImpl;
import com.pbl.back.service.impl.ProfileSkillServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SkillAndRecommendationOwnershipTest {

    @Test
    void profileSkillReadUpdateAndDeleteAreLimitedToTheCurrentProfile() {
        ProfileSkillRepository repository = mock(ProfileSkillRepository.class);
        SkillRepository skills = mock(SkillRepository.class);
        ProfileSkillMapper mapper = mock(ProfileSkillMapper.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        ProfileSkill row = ProfileSkill.builder()
                .id(3L)
                .profile(CandidateProfile.builder().user(User.builder().id(2L).build()).build())
                .skill(Skill.builder().id(5L).build())
                .build();
        ProfileSkillResponse response = mock(ProfileSkillResponse.class);
        when(repository.findById(3L)).thenReturn(Optional.of(row));
        when(repository.save(any(ProfileSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(skills.findById(5L)).thenReturn(Optional.of(row.getSkill()));
        when(currentUser.getId()).thenReturn(1L, 1L, 1L, 2L, 2L, 2L);
        when(mapper.toResponse(row)).thenReturn(response);
        ProfileSkillServiceImpl service = new ProfileSkillServiceImpl(
                repository, skills, mapper, mock(CandidateProfileRepository.class), currentUser);
        ProfileSkillRequest request = new ProfileSkillRequest();
        request.setSkillId(5L);

        assertThrows(ResourceNotFoundException.class, () -> service.getById(3L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(3L, request));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(3L));
        assertSame(response, service.getById(3L));
        assertSame(response, service.update(3L, request));
        service.delete(3L);
        verify(repository).delete(row);
    }

    @Test
    void cvSkillReadUpdateAndDeleteAreLimitedToTheCurrentCv() {
        CVSkillRepository repository = mock(CVSkillRepository.class);
        CVSkillMapper mapper = mock(CVSkillMapper.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        CVSkill row = CVSkill.builder()
                .id(4L)
                .cv(CV.builder().user(User.builder().id(2L).build()).build())
                .build();
        CVSkillResponse response = mock(CVSkillResponse.class);
        when(repository.findById(4L)).thenReturn(Optional.of(row));
        when(repository.save(any(CVSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(currentUser.getId()).thenReturn(1L, 1L, 1L, 2L, 2L, 2L);
        when(mapper.toResponse(row)).thenReturn(response);
        CVSkillServiceImpl service = new CVSkillServiceImpl(
                repository, mock(CVRepository.class), mock(SkillRepository.class), mapper, currentUser);

        assertThrows(ResourceNotFoundException.class, () -> service.getById(4L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(4L, new CVSkillRequest()));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(4L));
        assertSame(response, service.getById(4L));
        assertSame(response, service.update(4L, new CVSkillRequest()));
        service.delete(4L);
        verify(repository).delete(row);
    }

    @Test
    void recommendationsAreLimitedToTheCurrentUser() {
        JobRecommendationRepository repository = mock(JobRecommendationRepository.class);
        JobRecommendationMapper mapper = mock(JobRecommendationMapper.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        JobRecommendation row = JobRecommendation.builder()
                .id(6L)
                .user(User.builder().id(2L).build())
                .build();
        JobRecommendationResponse response = mock(JobRecommendationResponse.class);
        when(repository.findById(6L)).thenReturn(Optional.of(row));
        when(repository.save(any(JobRecommendation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(currentUser.getId()).thenReturn(1L, 1L, 1L, 2L, 2L, 2L);
        when(mapper.toResponse(row)).thenReturn(response);
        JobRecommendationServiceImpl service = new JobRecommendationServiceImpl(
                repository, mock(JobPostingRepository.class), mapper, currentUser);

        assertThrows(ResourceNotFoundException.class, () -> service.getById(6L));
        assertThrows(ResourceNotFoundException.class,
                () -> service.update(6L, new JobRecommendationRequest()));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(6L));
        assertSame(response, service.getById(6L));
        assertSame(response, service.update(6L, new JobRecommendationRequest()));
        service.delete(6L);
        verify(repository).delete(row);
    }
}
