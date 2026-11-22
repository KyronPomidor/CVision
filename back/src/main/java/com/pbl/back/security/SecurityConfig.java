package com.pbl.back.security;

import com.pbl.back.service.UserDetailsService;
import com.pbl.back.service.impl.JWTService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JWTAuthFilter jwtAuthenticationFilter,
            RateLimitFilter rateLimitFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/2fa/enable", "/api/auth/2fa/disable")
                        .authenticated()

                        .requestMatchers(
                                "/api/auth/**",
                                "/error",
                                "/scalar/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()

                        // Public registration only
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/postings/matches/me")
                        .hasRole("EMPLOYEE")

                        .requestMatchers("/api/cv/**").hasRole("EMPLOYEE")

                        .requestMatchers("/api/applications/me").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/applications/*").authenticated()
                        .requestMatchers("/api/applications/**").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/api/recommendations/job/*").hasRole("EMPLOYER")
                        .requestMatchers("/api/recommendations/**").hasRole("EMPLOYEE")

                        .requestMatchers("/api/profiles/me", "/api/profiles/me/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/profiles/*").hasAnyRole("EMPLOYEE", "EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/api/profiles/*/picture").hasRole("EMPLOYEE")
                        .requestMatchers("/api/profiles/**").hasRole("EMPLOYEE")

                        .requestMatchers("/api/companies/me", "/api/companies/me/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/api/companies/*", "/api/companies/*/picture")
                        .authenticated()
                        .requestMatchers("/api/companies/**").hasRole("EMPLOYER")

                        .requestMatchers(HttpMethod.GET, "/api/postings/*", "/api/postings/*/images/*")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/postings/*/applicants").hasRole("EMPLOYER")
                        .requestMatchers("/api/companies/me/postings/**").hasRole("EMPLOYER")
                        .requestMatchers("/api/postings/**").hasRole("EMPLOYER")

                        .requestMatchers("/api/skills/**").authenticated()

                        .requestMatchers("/api/users/me").authenticated()

                        .anyRequest().authenticated()
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Access denied\"}");
                        })
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(rateLimitFilter, JWTAuthFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public JWTAuthFilter jwtAuthenticationFilter(
            JWTService jwtService, UserDetailsService userDetailsService) {

        return new JWTAuthFilter(jwtService, userDetailsService);
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration(
            RateLimitFilter rateLimitFilter) {
        FilterRegistrationBean<RateLimitFilter> registration =
                new FilterRegistrationBean<>(rateLimitFilter);
        registration.setEnabled(false);
        return registration;
    }
}
