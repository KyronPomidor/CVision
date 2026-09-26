package com.pbl.back.security;

import com.pbl.back.service.RateLimiterService;
import com.pbl.back.service.impl.JWTService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateLimitFilter implements Filter {

    private static final String TOO_MANY_REQUESTS_BODY =
            "{\"error\":\"Rate limit exceeded. Please try again later.\"}";

    private final RateLimiterService rateLimiterService;
    private final JWTService jwtService;

    public RateLimitFilter(RateLimiterService rateLimiterService, JWTService jwtService) {
        this.rateLimiterService = rateLimiterService;
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isActuatorHealthRequest(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        if (!rateLimiterService.tryConsume(resolveKey(httpRequest))) {
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write(TOO_MANY_REQUESTS_BODY);
            return;
        }

        chain.doFilter(request, response);
    }

    private String resolveKey(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                return "user:" + jwtService.extractUserId(authHeader.substring(7));
            } catch (JwtException | IllegalArgumentException ignored) {
                // The JWT filter will reject invalid tokens; rate-limit this attempt by IP.
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)
                && !authentication.getName().isBlank()) {
            return "user:" + authentication.getName();
        }

        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            String clientAddress = forwardedFor.split(",", 2)[0].trim();
            if (!clientAddress.isEmpty()) {
                return "ip:" + clientAddress;
            }
        }
        return "ip:" + request.getRemoteAddr();
    }

    private boolean isActuatorHealthRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/actuator/health") || path.startsWith("/actuator/health/");
    }
}
