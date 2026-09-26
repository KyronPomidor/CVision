package com.pbl.back.service;

import com.pbl.back.domain.entity.User;
import com.pbl.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class EmailOtpService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final int expiryMinutes;

    public EmailOtpService(
            JavaMailSender mailSender,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            @Value("${app.otp.expiry-minutes:5}") int expiryMinutes) {
        if (expiryMinutes < 1) {
            throw new IllegalArgumentException("app.otp.expiry-minutes must be greater than zero");
        }
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.expiryMinutes = expiryMinutes;
    }

    public void generateAndSendCode(User user) {
        String code = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));
        user.setOtpCodeHash(passwordEncoder.encode(code));
        user.setOtpExpiresAt(LocalDateTime.now().plusMinutes(expiryMinutes));
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your verification code");
        message.setText("Your verification code is " + code + ". It expires in "
                + expiryMinutes + " minutes.");
        mailSender.send(message);
    }

    public boolean verifyCode(User user, String submittedCode) {
        if (user.getOtpExpiresAt() == null
                || !user.getOtpExpiresAt().isAfter(LocalDateTime.now())
                || user.getOtpCodeHash() == null
                || submittedCode == null
                || !passwordEncoder.matches(submittedCode, user.getOtpCodeHash())) {
            return false;
        }

        user.setOtpCodeHash(null);
        user.setOtpExpiresAt(null);
        userRepository.save(user);
        return true;
    }
}
