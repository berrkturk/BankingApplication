package com.banking.bankingrest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Account {

    @Id
    private UUID id;

    private UUID userId;

    private String number;

    private String name;

    private BigDecimal balance;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdatedAt;
}
