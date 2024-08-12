package com.banking.bankingrest.request.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionTransferRequest(String fromAccountNumber,
                                         String toAccountNumber,
                                         String name,
                                         BigDecimal amount) {
}
