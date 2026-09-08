package com.pbl.back.mapper;


import com.pbl.back.domain.entity.Application;
import com.pbl.back.dto.application.ApplicationRequest;
import com.pbl.back.dto.application.ApplicationResponse;

public interface ApplicationMapper {
    public Application toEntity(ApplicationRequest request);

    public ApplicationResponse toResponse(Application application);
}
