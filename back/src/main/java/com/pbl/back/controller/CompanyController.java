package com.pbl.back.controller;

import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@Valid @RequestBody CompanyRequest request) {
        return service.create(request);
    }

    @GetMapping("/me")
    public CompanyResponse getMe() {
        return service.getMe();
    }

    @PutMapping("/me")
    public CompanyResponse updateMe(@Valid @RequestBody CompanyRequest request) {
        return service.updateMe(request);
    }

    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping(value = "/me/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyResponse uploadPicture(@RequestPart("file") MultipartFile file) {
        return service.uploadPicture(file);
    }

    @GetMapping("/{id}/picture")
    public ResponseEntity<Resource> getPicture(@PathVariable Long id) {
        return service.getPicture(id);
    }

    @DeleteMapping("/me/picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePicture() {
        service.deletePicture();
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe() {
        service.deleteMe();
    }
}
