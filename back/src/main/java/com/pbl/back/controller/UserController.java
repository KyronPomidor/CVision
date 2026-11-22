package com.pbl.back.controller;

import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;
import com.pbl.back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return service.create(request);
    }

    @GetMapping("/me")
    public UserResponse getMe() {
        return service.getMe();
    }

    @PutMapping("/me")
    public UserResponse updateMe(@Valid @RequestBody UserRequest request) {
        return service.updateMe(request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe() {
        service.deleteMe();
    }
}