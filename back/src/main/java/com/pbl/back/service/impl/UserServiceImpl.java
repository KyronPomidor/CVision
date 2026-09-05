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

    @Override
    public UserResponse getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id)
                .orElseThrow();

        user.setEmail(request.getEmail());
        user.setAccountName(request.getAccountName());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return mapper.toResponse(repository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow();

        repository.delete(user);
    }
}
