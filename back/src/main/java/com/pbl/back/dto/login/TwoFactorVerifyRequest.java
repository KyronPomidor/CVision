package com.pbl.back.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoFactorVerifyRequest {
    private String preAuthToken;
    private String code;
}
