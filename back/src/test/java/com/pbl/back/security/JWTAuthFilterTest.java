package com.pbl.back.security;

import com.pbl.back.service.UserDetailsService;
import com.pbl.back.service.impl.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTAuthFilterTest {
    private final JWTService jwtService = mock(JWTService.class);
    private final UserDetailsService users = mock(UserDetailsService.class);
    private final JWTAuthFilter filter = new JWTAuthFilter(jwtService, users);
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final FilterChain chain = mock(FilterChain.class);

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticatedRequest() {
        request.setCookies(new Cookie("jwt", "token"));
        when(jwtService.extractUserId("token")).thenReturn(1L);
        when(users.loadUserByUserId(1L)).thenReturn(
                User.withUsername("user").password("unused").roles("EMPLOYEE").build());
    }

    @Test
    void downstreamServerFailureIsNotConvertedToUnauthorized() throws Exception {
        authenticatedRequest();
        ServletException failure = new ServletException("Database failure");
        doThrow(failure).when(chain).doFilter(request, response);

        assertSame(failure, assertThrows(ServletException.class,
                () -> filter.doFilter(request, response, chain)));
        assertFalse(response.isCommitted());
        assertNotEquals(401, response.getStatus());
    }

    @Test
    void userLookupInfrastructureFailureIsNotConvertedToUnauthorized() {
        request.setCookies(new Cookie("jwt", "token"));
        when(jwtService.extractUserId("token")).thenReturn(1L);
        IllegalStateException failure = new IllegalStateException("Database unavailable");
        when(users.loadUserByUserId(1L)).thenThrow(failure);

        assertSame(failure, assertThrows(IllegalStateException.class,
                () -> filter.doFilter(request, response, chain)));
        assertNotEquals(401, response.getStatus());
    }

    @Test
    void invalidTokenStillReturnsUnauthorized() throws Exception {
        request.setCookies(new Cookie("jwt", "invalid"));
        when(jwtService.extractUserId("invalid"))
                .thenThrow(new io.jsonwebtoken.MalformedJwtException("Invalid token"));

        filter.doFilter(request, response, chain);

        assertEquals(401, response.getStatus());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verifyNoInteractions(chain, users);
    }

    @Test
    void validTokenAuthenticatesAndContinues() throws Exception {
        authenticatedRequest();

        filter.doFilter(request, response, chain);

        assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        verify(chain).doFilter(request, response);
    }
}
