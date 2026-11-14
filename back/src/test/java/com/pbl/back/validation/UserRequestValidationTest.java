package com.pbl.back.validation;

import com.pbl.back.domain.enums.Role;
import com.pbl.back.dto.user.UserRequest;
import org.junit.jupiter.api.Test;

class UserRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesAccountNameEmailPasswordAndRole() {
        UserRequest request = validRequest();
        request.setAccountName("ab");
        assertInvalid(request, "accountName", "3-50");
        request.setAccountName("abc");
        assertValid(request);
        request.setAccountName("x".repeat(51));
        assertInvalid(request, "accountName", "3-50");
        request.setAccountName("abc");

        request.setEmail("a@b");
        assertInvalid(request, "email", "valid email");
        request.setEmail("a@b.co");
        assertValid(request);
        request.setEmail("a".repeat(250) + "@b.co");
        assertInvalid(request, "email", "254");
        request.setEmail("a@b.co");
        request.setEmail("");
        assertInvalid(request, "email", "required");
        request.setEmail("a@b.co");

        request.setPassword("1234567");
        assertInvalid(request, "password", "8-72");
        request.setPassword("12345678");
        assertValid(request);
        request.setPassword("x".repeat(73));
        assertInvalid(request, "password", "8-72");

        request.setRole(null);
        assertInvalid(request, "role", "required");
    }

    private UserRequest validRequest() {
        UserRequest request = new UserRequest();
        request.setAccountName("abc");
        request.setEmail("a@b.co");
        request.setPassword("12345678");
        request.setRole(Role.EMPLOYEE);
        return request;
    }
}
