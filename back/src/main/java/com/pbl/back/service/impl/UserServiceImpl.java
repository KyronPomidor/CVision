package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;
import com.pbl.back.exception.ConflictException;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.UserMapper;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    @Override
    public UserResponse create(UserRequest request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    throw new ConflictException(
                            "A user with email '" + request.getEmail() + "' already exists."
                    );
                });

        User user = mapper.toEntity(request);

        User savedUser = repository.save(user);

        return mapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getMe() {
        return mapper.toResponse(getCurrentUser());
    }

    @Override
    public UserResponse updateMe(UserRequest request) {
        User user = getCurrentUser();

        if (!user.getEmail().equals(request.getEmail())) {
            repository.findByEmail(request.getEmail())
                    .filter(existing -> !existing.getId().equals(user.getId()))
                    .ifPresent(existing -> {
                        throw new ConflictException(
                                "A user with email '" + request.getEmail() + "' already exists."
                        );
                    });
        }

        user.setEmail(request.getEmail());
        user.setAccountName(request.getAccountName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return mapper.toResponse(repository.save(user));
    }

    @Override
    public void deleteMe() {
        repository.delete(getCurrentUser());
    }

    private User getCurrentUser() {
        Long id = currentUserService.getId();
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + id + "' not found."));
    }
}
