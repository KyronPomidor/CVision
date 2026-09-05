package com.pbl.back.controller;

import com.pbl.back.dto.user.UserRequest;
import com.pbl.back.dto.user.UserResponse;
import com.pbl.back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }
}