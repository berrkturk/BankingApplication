package com.banking.bankingrest.request.account;

import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.List;

public record AccountSearchByFiltersRequest(List<@NotBlank String> numbers,
                                            List<@NotBlank String> names) {

    public AccountSearchByFiltersRequest {
        if (numbers.isEmpty()) {
            numbers = Collections.emptyList();
        }
        if (names.isEmpty()) {
            names = Collections.emptyList();
        }
    }
}
