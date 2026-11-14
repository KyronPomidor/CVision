package com.pbl.back.validation;

import com.pbl.back.dto.login.LoginRequest;
import com.pbl.back.dto.login.PreAuthTokenRequest;
import com.pbl.back.dto.login.TwoFactorVerifyRequest;
import org.junit.jupiter.api.Test;

class AuthRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesLoginFieldsAsRequiredOnly() {
        LoginRequest request = new LoginRequest();
        request.setEmail("");
        request.setPassword("");
        assertInvalid(request, "email", "required");
        assertInvalid(request, "password", "required");

        request.setEmail("not-an-email");
        request.setPassword("x");
        assertValid(request);
    }

    @Test
    void validatesTwoFactorTokenAndSixDigitCode() {
        TwoFactorVerifyRequest request = new TwoFactorVerifyRequest();
        request.setPreAuthToken("");
        request.setCode("12345");
        assertInvalid(request, "preAuthToken", "required");
        assertInvalid(request, "code", "6-digit");

        request.setPreAuthToken("token");
        request.setCode("123456");
        assertValid(request);
    }

    @Test
    void validatesResendTokenAsRequired() {
        PreAuthTokenRequest request = new PreAuthTokenRequest();
        assertInvalid(request, "preAuthToken", "required");
        request.setPreAuthToken("token");
        assertValid(request);
    }
}
