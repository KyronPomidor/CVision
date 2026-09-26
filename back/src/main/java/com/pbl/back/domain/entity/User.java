package com.pbl.back.domain.entity;

import com.pbl.back.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(name = "two_factor_enabled", nullable = false, columnDefinition = "boolean default false")
    private boolean twoFactorEnabled = false;

    @Column(name = "otp_code_hash")
    private String otpCodeHash;

    @Column(name = "otp_expires_at")
    private LocalDateTime otpExpiresAt;
}
