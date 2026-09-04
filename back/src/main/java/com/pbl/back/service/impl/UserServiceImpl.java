package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;
import com.pbl.back.mapper.UserMapper;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserResponse create(UserRequest request) {

        User user = mapper.toEntity(request);

        User savedUser = repository.save(user);

        return mapper.toResponse(savedUser);
    }
}
