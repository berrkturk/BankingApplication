package com.banking.bankingrest.request.user;

import com.banking.bankingrest.enums.Roles;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.StringUtils;

import java.util.List;

public record UserRegisterRequest(@NotBlank String username,
                                  @NotBlank String password,
                                  @NotBlank String email,
                                  String role) {

    public UserRegisterRequest {
        if (!StringUtils.hasText(role)) {
            role = Roles.USER.name();
        }
    }
}
