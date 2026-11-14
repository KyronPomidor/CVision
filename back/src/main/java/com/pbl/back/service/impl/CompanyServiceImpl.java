package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.CompanyMapper;
import com.pbl.back.repository.CompanyRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CompanyService;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.ImageStorageService;
import com.pbl.back.service.ImageStorageService.StoredImage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final ImageStorageService imageStorageService;

    @Override
    public CompanyResponse create(Long userId, CompanyRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found."));

        Company company = mapper.toEntity(request);

        company.setUser(user);

        Company savedCompany = repository.save(company);

        return mapper.toResponse(savedCompany);
    }

    @Override
    public CompanyResponse getByUserId(Long userId) {
        Company company = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company for user with id '" + userId + "' not found."));

        return mapper.toResponse(company);
    }

    @Override
    public CompanyResponse getById(Long id) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with id '" + id + "' not found."));

        return mapper.toResponse(company);
    }

    @Override
    public CompanyResponse update(Long id, CompanyRequest request) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with id '" + id + "' not found."));

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setLocation(request.getLocation());
        company.setLogo(request.getLogo());
        company.setPhotos(request.getPhotos());

        company.getDetails().setWebsite(request.getDetails().getWebsite());
        company.getDetails().setEmail(request.getDetails().getEmail());
        company.getDetails().setAddress(request.getDetails().getAddress());
        company.getDetails().setSize(request.getDetails().getSize());
        company.getDetails().setFounded(request.getDetails().getFounded());
        company.getDetails().setLinkedin(request.getDetails().getLinkedin());
        company.getDetails().setInstagram(request.getDetails().getInstagram());
        company.getDetails().setTwitter(request.getDetails().getTwitter());
        company.getDetails().setFacebook(request.getDetails().getFacebook());

        return mapper.toResponse(repository.save(company));
    }

    @Override
    public void delete(Long id) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with id '" + id + "' not found."));

        repository.delete(company);
        imageStorageService.deleteDirectoryQuietly("companies/" + id);
    }

    @Override
    public CompanyResponse uploadPicture(MultipartFile file) {
        Long userId = currentUserService.getId();
        Company company = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Create your company profile first."));
        String oldPicturePath = company.getPicturePath();
        StoredImage newImage = imageStorageService.store(file, "companies/" + company.getId());

        company.setPicturePath(newImage.relativePath());
        company.setPictureContentType(newImage.contentType());
        try {
            repository.save(company);
        } catch (RuntimeException ex) {
            imageStorageService.deleteQuietly(newImage.relativePath());
            throw ex;
        }

        if (oldPicturePath != null) {
            imageStorageService.deleteQuietly(oldPicturePath);
        }
        return mapper.toResponse(company);
    }

    @Override
    public ResponseEntity<Resource> getPicture(Long id) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with id '" + id + "' not found."));
        if (company.getPicturePath() == null) {
            throw new ResourceNotFoundException("Company picture not found.");
        }
        return inlineImage(company.getPicturePath(), company.getPictureContentType());
    }

    @Override
    public void deletePicture() {
        Company company = repository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your company profile first."));
        String oldPicturePath = company.getPicturePath();
        company.setPicturePath(null);
        company.setPictureContentType(null);
        repository.save(company);
        if (oldPicturePath != null) {
            imageStorageService.deleteQuietly(oldPicturePath);
        }
    }

    private ResponseEntity<Resource> inlineImage(String filePath, String contentType) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline().build().toString())
                .cacheControl(CacheControl.noCache().cachePrivate())
                .body(imageStorageService.load(filePath));
    }
}
