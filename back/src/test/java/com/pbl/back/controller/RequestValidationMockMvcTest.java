package com.pbl.back.controller;

import com.pbl.back.exception.GlobalExceptionHandler;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.EmailOtpService;
import com.pbl.back.service.UserService;
import com.pbl.back.service.impl.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RequestValidationMockMvcTest {

    @Test
    void userCreateWithShortPasswordReturnsReadableBadRequest() throws Exception {
        UserController controller = new UserController(mock(UserService.class));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "accountName": "tester",
                                  "email": "test@example.com",
                                  "password": "short",
                                  "role": "EMPLOYEE"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("password: ")))
                .andExpect(content().string(containsString("8")));
    }

    @Test
    void loginWithoutEmailReturnsBadRequest() throws Exception {
        AuthController controller = new AuthController(
                mock(AuthenticationManager.class),
                mock(JWTService.class),
                mock(UserRepository.class),
                mock(EmailOtpService.class),
                mock(CurrentUserService.class));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "password": "anything"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("email: ")));
    }
}
