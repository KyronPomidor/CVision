package com.pbl.back.controller;

import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.LoginResponse;
import com.pbl.back.dto.login.LoginRequest;
import com.pbl.back.dto.login.PreAuthTokenRequest;
import com.pbl.back.dto.login.TwoFactorRequiredResponse;
import com.pbl.back.dto.login.TwoFactorVerifyRequest;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.EmailOtpService;
import com.pbl.back.service.impl.JWTService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final EmailOtpService emailOtpService;
    private final CurrentUserService currentUserService;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService,
                          UserRepository userRepository, EmailOtpService emailOtpService,
                          CurrentUserService currentUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.emailOtpService = emailOtpService;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.isTwoFactorEnabled()) {
            emailOtpService.generateAndSendCode(user);
            return ResponseEntity.ok(new TwoFactorRequiredResponse(
                    true, jwtService.generatePreAuthToken(user)));
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<?> verifyTwoFactor(@RequestBody TwoFactorVerifyRequest request) {
        Long userId;
        try {
            userId = jwtService.parsePreAuthToken(request.getPreAuthToken());
        } catch (JwtException | IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired pre-auth token");
        }

        User user = userRepository.findById(userId)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired pre-auth token");
        }
        if (!user.isTwoFactorEnabled()) {
            return ResponseEntity.badRequest().body("Two-factor authentication is not enabled");
        }
        if (!emailOtpService.verifyCode(user, request.getCode())) {
            return ResponseEntity.badRequest().body("Invalid or expired code");
        }

        return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(user)));
    }

    @PostMapping("/2fa/resend")
    public ResponseEntity<?> resendTwoFactorCode(@RequestBody PreAuthTokenRequest request) {
        User user = findUserForPreAuthToken(request.getPreAuthToken());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired pre-auth token");
        }
        if (!user.isTwoFactorEnabled()) {
            return ResponseEntity.badRequest().body("Two-factor authentication is not enabled");
        }

        emailOtpService.generateAndSendCode(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/2fa/enable")
    public ResponseEntity<Void> enableTwoFactor() {
        User user = currentUserService.get();
        user.setTwoFactorEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/2fa/disable")
    public ResponseEntity<Void> disableTwoFactor() {
        User user = currentUserService.get();
        user.setTwoFactorEnabled(false);
        user.setOtpCodeHash(null);
        user.setOtpExpiresAt(null);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    private User findUserForPreAuthToken(String token) {
        try {
            Long userId = jwtService.parsePreAuthToken(token);
            return userRepository.findById(userId).orElse(null);
        } catch (JwtException | IllegalArgumentException exception) {
            return null;
        }
    }
}