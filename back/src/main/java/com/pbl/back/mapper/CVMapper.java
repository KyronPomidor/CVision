package com.pbl.back.mapper;

import com.pbl.back.domain.entity.CV;
import com.pbl.back.dto.cv.CVRequest;
import com.pbl.back.dto.cv.CVResponse;

public interface CVMapper {
    CV toEntity(CVRequest request);

    CVResponse toResponse(CV cv);
}
