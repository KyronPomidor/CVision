package com.pbl.back.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoFactorVerifyRequest {
    @NotBlank(message = "is required")
    private String preAuthToken;

    @NotBlank(message = "is required")
    @Pattern(regexp = "^\\d{6}$", message = "must be a 6-digit code")
    private String code;
}
