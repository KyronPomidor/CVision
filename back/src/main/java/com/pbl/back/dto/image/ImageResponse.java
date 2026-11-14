package com.pbl.back.dto.image;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponse {
    private Long id;
    private String url;
    private Integer sortOrder;
}
