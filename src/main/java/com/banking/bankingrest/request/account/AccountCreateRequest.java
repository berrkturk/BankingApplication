package com.banking.bankingrest.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AccountCreateRequest(@NotNull UUID userId,
                                   @NotBlank String number,
                                   @NotBlank String name) {
}
