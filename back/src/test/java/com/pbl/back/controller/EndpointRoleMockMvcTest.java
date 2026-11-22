package com.pbl.back.controller;

import com.pbl.back.service.CompanyService;
import com.pbl.back.service.CandidateProfileService;
import com.pbl.back.service.ApplicationService;
import com.pbl.back.service.CVSkillService;
import com.pbl.back.service.JobPostingService;
import com.pbl.back.service.JobRecommendationService;
import com.pbl.back.service.ProfileSkillService;
import com.pbl.back.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointRoleMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompanyService companyService;

    @MockitoBean
    private CandidateProfileService candidateProfileService;

    @MockitoBean
    private JobPostingService jobPostingService;

    @MockitoBean
    private JobRecommendationService jobRecommendationService;

    @MockitoBean
    private ApplicationService applicationService;

    @MockitoBean
    private CVSkillService cvSkillService;

    @MockitoBean
    private ProfileSkillService profileSkillService;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void employeeCannotAccessEmployerCompanyProfile() throws Exception {
        mockMvc.perform(get("/api/companies/me"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void employerCanReadOwnCompanyProfileEndpoint() throws Exception {
        mockMvc.perform(get("/api/companies/me"))
                .andExpect(status().isOk());

        verify(companyService).getMe();
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void authenticatedEmployeeCanReadCompanyBehindPosting() throws Exception {
        mockMvc.perform(get("/api/companies/41"))
                .andExpect(status().isOk());

        verify(companyService).getById(41L);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void oldUserScopedCompanyRouteIsNoLongerMapped() throws Exception {
        mockMvc.perform(get("/api/companies/user/41"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void employerCanReadCandidateProfileForApplicationReview() throws Exception {
        mockMvc.perform(get("/api/profiles/41"))
                .andExpect(status().isOk());

        verify(candidateProfileService).getById(41L);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void employerCannotUseEmployeeProfileMeEndpoint() throws Exception {
        mockMvc.perform(get("/api/profiles/me"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void employeeCanReadSharedJobPosting() throws Exception {
        mockMvc.perform(get("/api/postings/41"))
                .andExpect(status().isOk());

        verify(jobPostingService).getById(41L);
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void recommendationRoutesAreTemporarilyUnavailable() throws Exception {
        mockMvc.perform(get("/api/recommendations/job/41"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void usersMeRouteIsAvailableAndLegacyIdRouteIsGone() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk());
        verify(userService).getMe();

        mockMvc.perform(get("/api/users/41"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void employeeCanUseOwnProfileAndCvSkillRoutes() throws Exception {
        mockMvc.perform(get("/api/profiles/me/skills"))
                .andExpect(status().isOk());
        verify(profileSkillService).getSkills();

        mockMvc.perform(get("/api/cv/me/skills"))
                .andExpect(status().isOk());
        verify(cvSkillService).getMine();
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void employerCannotUseEmployeeCvOrApplicationMeRoutes() throws Exception {
        mockMvc.perform(get("/api/cv/me"))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/cv/me/skills"))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/applications/me"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void employeeCanReadOwnApplicationAndEmployerPostingCollectionIsDenied() throws Exception {
        mockMvc.perform(get("/api/applications/me"))
                .andExpect(status().isOk());
        verify(applicationService).getMine();

        mockMvc.perform(get("/api/companies/me/postings"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void employerCanReadApplicantsRoute() throws Exception {
        mockMvc.perform(get("/api/postings/41/applicants"))
                .andExpect(status().isOk());
        verify(applicationService).getApplicantsForPosting(41L);
    }
}
