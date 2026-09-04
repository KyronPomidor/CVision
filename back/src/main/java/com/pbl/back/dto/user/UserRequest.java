package com.pbl.back.dto.user;

import com.pbl.back.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String email;
    @NotBlank
    private String accountName;
    @NotBlank
    private String password;
    private Role role;
}
