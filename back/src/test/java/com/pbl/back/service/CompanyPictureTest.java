package com.pbl.back.service;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.mapper.CompanyMapper;
import com.pbl.back.repository.CompanyRepository;
import com.pbl.back.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyPictureTest {

    @TempDir
    Path baseDirectory;

    @Test
    void uploadingCompanyPictureReplacesPreviousImage() throws Exception {
        CompanyRepository companies = mock(CompanyRepository.class);
        CompanyMapper mapper = mock(CompanyMapper.class);
        CurrentUserService currentUserService = mock(CurrentUserService.class);
        Company company = Company.builder()
                .id(12L)
                .user(User.builder().id(7L).build())
                .name("Company")
                .picturePath("companies/12/old.png")
                .pictureContentType("image/png")
                .build();
        Path companyDirectory = baseDirectory.resolve("companies").resolve("12");
        Files.createDirectories(companyDirectory);
        Path oldFile = companyDirectory.resolve("old.png");
        Files.writeString(oldFile, "old picture");

        when(currentUserService.getId()).thenReturn(7L);
        when(companies.findByUserId(7L)).thenReturn(Optional.of(company));
        when(companies.save(any(Company.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(company)).thenReturn(CompanyResponse.builder()
                .id(12L)
                .pictureUrl("/api/companies/12/picture")
                .build());
        CompanyServiceImpl service = new CompanyServiceImpl(
                companies,
                mapper,
                currentUserService,
                new ImageStorageService(baseDirectory.toString()));

        CompanyResponse response = service.uploadPicture(new MockMultipartFile(
                "file",
                "new.png",
                "image/png",
                new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}));

        assertFalse(Files.exists(oldFile));
        assertEquals("/api/companies/12/picture", response.getPictureUrl());
        assertEquals("image/png", company.getPictureContentType());
        verify(companies).save(company);
    }
}
