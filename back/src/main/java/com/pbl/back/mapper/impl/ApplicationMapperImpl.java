package com.pbl.back.mapper.impl;

import com.pbl.back.domain.entity.Application;
import com.pbl.back.dto.application.ApplicationRequest;
import com.pbl.back.dto.application.ApplicationResponse;
import com.pbl.back.mapper.ApplicationMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApplicationMapperImpl implements ApplicationMapper {

    @Override
    public Application toEntity(ApplicationRequest request) {
        return Application.builder()
                .status(request.getStatus())
                .build();
    }

    @Override
    public ApplicationResponse toResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .userId(application.getUser().getId())
                .jobPostingId(application.getJobPosting().getId())
                .cvId(application.getCv().getId())
                .status(application.getStatus())
                .appliedAt(LocalDateTime.now())
                .build();
    }
}
