package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.User;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {
    private final UserRepository userRepository;

    @Override
    public User get() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new AccessDeniedException("Authenticated user not found"));
    }

    @Override
    public Long getId() {
        return get().getId();
    }
}