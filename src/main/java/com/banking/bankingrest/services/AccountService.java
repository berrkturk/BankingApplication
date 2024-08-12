package com.banking.bankingrest.services;

import com.banking.bankingrest.entity.Account;
import com.banking.bankingrest.exception.AccountNotFoundException;
import com.banking.bankingrest.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account create(UUID userId, String number, String name) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Account account = new Account(id, userId, number, name, BigDecimal.ZERO, now, now);
        repository.save(account);
        return account;
    }

    public List<Account> searchByFilters(List<String> numbers, List<String> names) {
        return repository.findByNumbersAndNames(numbers, names);
    }

    public Account update(UUID id, String name) throws AccountNotFoundException {
        Account account = getById(id);
        account.setName(name);
        account.setLastUpdatedAt(LocalDateTime.now());
        repository.save(account);
        return account;
    }

    public void updateMoney(Account account, BigDecimal newBalance) {
        account.setBalance(newBalance);
        account.setLastUpdatedAt(LocalDateTime.now());
        repository.save(account);
    }

    public void delete(UUID id) throws AccountNotFoundException {
        Account account = getById(id);
        repository.delete(account);
    }

    public Account getByNumber(String number) throws AccountNotFoundException {
        Optional<Account> optionalAccount = repository.findByNumber(number);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }
        return optionalAccount.get();
    }

    public Account getById(UUID id) throws AccountNotFoundException {
        Optional<Account> optionalAccount = repository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }
        return optionalAccount.get();
    }
}
