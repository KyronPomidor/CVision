package com.pbl.back.service;

import com.pbl.back.domain.entity.User;
import com.pbl.back.domain.enums.Role;
import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.exception.ConflictException;
import com.pbl.back.mapper.UserMapper;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceEmailUpdateTest {

    @Test
    void updateRejectsEmailOwnedByAnotherUser() {
        UserRepository repository = mock(UserRepository.class);
        UserMapper mapper = mock(UserMapper.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        CurrentUserService currentUserService = mock(CurrentUserService.class);
        User currentUser = User.builder().id(1L).email("current@example.com").build();
        User anotherUser = User.builder().id(2L).email("taken@example.com").build();
        when(currentUserService.getId()).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(currentUser));
        when(repository.findByEmail("taken@example.com")).thenReturn(Optional.of(anotherUser));
        UserRequest request = new UserRequest();
        request.setAccountName("Candidate");
        request.setEmail("taken@example.com");
        request.setPassword("password123");
        request.setRole(Role.EMPLOYEE);
        UserServiceImpl service = new UserServiceImpl(repository, mapper, passwordEncoder, currentUserService);

        assertThrows(ConflictException.class, () -> service.update(1L, request));

        verify(repository, never()).save(currentUser);
    }
}
