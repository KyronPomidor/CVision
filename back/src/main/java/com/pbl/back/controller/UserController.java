package com.pbl.back.controller;

import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;
import com.pbl.back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {

        return service.create(request);
    }
}