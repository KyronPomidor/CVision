package com.pbl.back.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TwoFactorRequiredResponse {
    private boolean twoFactorRequired;
    private String preAuthToken;
}
