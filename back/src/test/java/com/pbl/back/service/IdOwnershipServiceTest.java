package com.pbl.back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl.back.domain.entity.*;
import com.pbl.back.dto.application.ApplicationRequest;
import com.pbl.back.dto.application.ApplicationResponse;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.ApplicationMapper;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.mapper.CVMapper;
import com.pbl.back.repository.ApplicationRepository;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.CVRepository;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.impl.ApplicationServiceImpl;
import com.pbl.back.service.impl.CandidateProfileServiceImpl;
import com.pbl.back.service.impl.CVServiceImpl;
import com.pbl.back.mapper.ProfileSkillMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IdOwnershipServiceTest {

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void cvReadsAndWritesHideAnotherUsersRecordAndAllowTheOwner() {
        CVRepository repository = mock(CVRepository.class);
        CVMapper mapper = mock(CVMapper.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        CV cv = CV.builder().id(8L).user(User.builder().id(2L).build()).build();
        CVResponse response = mock(CVResponse.class);
        when(repository.findById(8L)).thenReturn(Optional.of(cv));
        when(repository.save(any(CV.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(currentUser.getId()).thenReturn(1L, 1L, 1L, 2L, 2L, 2L);
        when(mapper.toResponse(any(CV.class))).thenReturn(response);
        CVServiceImpl service = new CVServiceImpl(
                repository, mapper, mock(UserRepository.class), currentUser,
                mock(PerplexityService.class), mock(CvSkillSyncService.class), new ObjectMapper());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(8L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(8L, new CVRequest()));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(8L));
        assertSame(response, service.getById(8L));
        assertSame(response, service.update(8L, new CVRequest()));
        service.delete(8L);
        verify(repository).delete(cv);
    }

    @Test
    void applicationsAreOwnerOnlyExceptForTheirPostingEmployer() {
        ApplicationRepository repository = mock(ApplicationRepository.class);
        ApplicationMapper mapper = mock(ApplicationMapper.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        Application application = Application.builder()
                .id(9L)
                .user(User.builder().id(2L).build())
                .jobPosting(JobPosting.builder()
                        .company(Company.builder().user(User.builder().id(1L).build()).build())
                        .build())
                .build();
        ApplicationResponse response = mock(ApplicationResponse.class);
        when(repository.findById(9L)).thenReturn(Optional.of(application));
        when(repository.save(any(Application.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(currentUser.getId()).thenReturn(2L, 2L, 2L, 3L, 3L, 3L, 1L, 4L);
        when(mapper.toResponse(any(Application.class))).thenReturn(response);
        ApplicationServiceImpl service = new ApplicationServiceImpl(
                mapper, repository, mock(CVRepository.class), mock(JobPostingRepository.class), currentUser);
        ApplicationRequest request = new ApplicationRequest();
        SecurityContextHolder.getContext().setAuthentication(authentication("ROLE_EMPLOYEE"));

        assertSame(response, service.getById(9L));
        assertSame(response, service.update(9L, request));
        service.delete(9L);
        verify(repository).delete(application);
        assertThrows(ResourceNotFoundException.class, () -> service.getById(9L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(9L, request));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(9L));

        SecurityContextHolder.getContext().setAuthentication(authentication("ROLE_EMPLOYER"));
        assertSame(response, service.getById(9L));
        assertThrows(AccessDeniedException.class, () -> service.getById(9L));
    }

    @Test
    void candidateProfileIsPrivateExceptToAnEmployerWithAnApplication() {
        CandidateProfileRepository profiles = mock(CandidateProfileRepository.class);
        ApplicationRepository applications = mock(ApplicationRepository.class);
        ProfileSkillRepository skills = mock(ProfileSkillRepository.class);
        CandidateProfileMapper mapper = mock(CandidateProfileMapper.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        CandidateProfile profile = CandidateProfile.builder()
                .id(44L)
                .user(User.builder().id(2L).build())
                .build();
        CandidateProfileResponse response = mock(CandidateProfileResponse.class);
        when(profiles.findById(44L)).thenReturn(Optional.of(profile));
        when(skills.findByProfileId(44L)).thenReturn(List.of());
        when(applications.existsByUserIdAndJobPostingCompanyUserId(2L, 9L)).thenReturn(true);
        when(applications.existsByUserIdAndJobPostingCompanyUserId(2L, 10L)).thenReturn(false);
        when(currentUser.getId()).thenReturn(1L, 9L, 10L, 2L);
        when(mapper.toResponse(any(CandidateProfile.class), anyList())).thenReturn(response);
        CandidateProfileServiceImpl service = new CandidateProfileServiceImpl(
                profiles, mapper, applications, skills, mock(ProfileSkillMapper.class),
                currentUser, mock(ImageStorageService.class));

        SecurityContextHolder.getContext().setAuthentication(authentication("ROLE_EMPLOYEE"));
        assertThrows(ResourceNotFoundException.class, () -> service.getById(44L));

        SecurityContextHolder.getContext().setAuthentication(authentication("ROLE_EMPLOYER"));
        assertSame(response, service.getById(44L));
        assertThrows(AccessDeniedException.class, () -> service.getById(44L));

        SecurityContextHolder.getContext().setAuthentication(authentication("ROLE_EMPLOYEE"));
        assertSame(response, service.getById(44L));
    }

    private UsernamePasswordAuthenticationToken authentication(String role) {
        return new UsernamePasswordAuthenticationToken(
                "test@example.com", null, List.of(new SimpleGrantedAuthority(role)));
    }
}
