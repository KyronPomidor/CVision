package com.pbl.back.controller;

import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}")
    public CompanyResponse create(@PathVariable Long userId,
                                  @Valid @RequestBody CompanyRequest request) {
        return service.create(userId, request);
    }

    @GetMapping("/user/{userId}")
    public CompanyResponse getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PutMapping("/{id}")
    public CompanyResponse update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
