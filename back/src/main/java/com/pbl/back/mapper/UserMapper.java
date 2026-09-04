package com.pbl.back.mapper;

import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;

public interface UserMapper {
    User toEntity(UserRequest request);

    UserResponse toResponse(User user);
}
