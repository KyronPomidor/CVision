package com.pbl.back.dto.user;

import com.pbl.back.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "is required")
    @Size(min = 3, max = 50, message = "must be 3-50 characters")
    private String accountName;

    @NotBlank(message = "is required")
    @Size(max = 254, message = "must be at most 254 characters")
    @Email(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$",
            message = "must be a valid email address")
    private String email;

    @NotBlank(message = "is required")
    @Size(min = 8, max = 72, message = "must be 8-72 characters")
    private String password;

    @NotNull(message = "is required")
    private Role role;
}
