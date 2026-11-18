package com.pbl.back.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreAuthTokenRequest {
    @NotBlank(message = "is required")
    private String preAuthToken;
}
