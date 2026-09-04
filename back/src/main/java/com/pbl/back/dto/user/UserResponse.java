package com.pbl.back.dto.user;

import com.pbl.back.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String accountName;
    private Role role;
    private LocalDateTime createdAt;
}
