package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;
import com.pbl.back.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toEntity(UserRequest request) {

        return User.builder()
                .email(request.getEmail())
                .accountName(request.getAccountName())
                .password(request.getPassword())
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .accountName(user.getAccountName())
                .role(user.getRole())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
