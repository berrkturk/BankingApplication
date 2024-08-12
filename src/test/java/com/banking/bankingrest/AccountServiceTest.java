package com.banking.bankingrest;

import com.banking.bankingrest.entity.Account;
import com.banking.bankingrest.exception.AccountNotFoundException;
import com.banking.bankingrest.repository.AccountRepository;
import com.banking.bankingrest.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountById() throws AccountNotFoundException {
        UUID uuid = randomUUID();
        Account account = createTestAccount(uuid, "12345", "John Doe", BigDecimal.valueOf(1000.0));

        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));

        Account result = accountService.getById(uuid);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testCreateAccount() {
        UUID uuid = randomUUID();
        Account account = createTestAccount(null, "12345", "John Doe", BigDecimal.valueOf(1000.0));

        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.create(uuid, "12345", "John Doe");
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateAccount() throws AccountNotFoundException {
        UUID uuid = randomUUID();
        Account existingAccount = createTestAccount(uuid, "12345", "John Doe", BigDecimal.valueOf(1000.0));
        Account updatedAccount = createTestAccount(null, "12345", "John Smith", BigDecimal.valueOf(1000.0));

        updatedAccount.setNumber("12345");
        updatedAccount.setName("John Smith");

        when(accountRepository.findById(uuid)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(existingAccount);

        Account result = accountService.update(uuid, updatedAccount.getName());
        assertNotNull(result);
        assertEquals("John Smith", result.getName());
    }

    private Account createTestAccount(UUID id, String number, String name, BigDecimal balance) {
        Account account = new Account();
        account.setId(id);
        account.setNumber(number);
        account.setName(name);
        account.setBalance(balance);
        return account;
    }
}
