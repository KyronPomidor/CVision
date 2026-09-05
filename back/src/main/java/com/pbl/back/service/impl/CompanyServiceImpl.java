package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.CandidateProfile;
import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.User;
import com.pbl.back.dto.company.CompanyRequest;
import com.pbl.back.dto.company.CompanyResponse;
import com.pbl.back.mapper.CandidateProfileMapper;
import com.pbl.back.mapper.CompanyMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.CompanyRepository;
import com.pbl.back.repository.UserRepository;
import com.pbl.back.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;
    private final UserRepository userRepository;

    @Override
    public CompanyResponse create(Long userId, CompanyRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow();

        Company company = mapper.toEntity(request);

        company.setUser(user);

        Company savedCompany = repository.save(company);

        return mapper.toResponse(savedCompany);
    }

    @Override
    public CompanyResponse getByUserId(Long userId) {
        Company company = repository.findByUserId(userId)
                .orElseThrow();

        return mapper.toResponse(company);
    }

    @Override
    public CompanyResponse getById(Long id) {
        Company company = repository.findById(id)
                .orElseThrow();

        return mapper.toResponse(company);
    }

    @Override
    public CompanyResponse update(Long id, CompanyRequest request) {
        Company company = repository.findById(id)
                .orElseThrow();

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());

        return mapper.toResponse(repository.save(company));
    }

    @Override
    public void delete(Long id) {
        Company company = repository.findById(id)
                .orElseThrow();

        repository.delete(company);
    }
}
