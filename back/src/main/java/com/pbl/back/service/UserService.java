package com.pbl.back.service;

import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;

public interface UserService {
    UserResponse create(UserRequest request);

    UserResponse getMe();

    UserResponse updateMe(UserRequest request);

    void deleteMe();
}
