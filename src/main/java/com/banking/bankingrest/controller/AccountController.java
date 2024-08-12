package com.banking.bankingrest.controller;

import com.banking.bankingrest.exception.AccountNotFoundException;
import com.banking.bankingrest.entity.Account;
import com.banking.bankingrest.request.account.AccountCreateRequest;
import com.banking.bankingrest.request.account.AccountSearchByFiltersRequest;
import com.banking.bankingrest.services.AccountService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "Account API", tags = "Account")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Validated @RequestBody AccountCreateRequest request) {
        Account account = accountService.create(request.userId(), request.number(), request.name());
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Account>> searchAccountsByFilters(
            @Validated @RequestBody AccountSearchByFiltersRequest request) {
        List<Account> accounts = accountService.searchByFilters(request.numbers(), request.names());
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID id, @RequestParam String name)
            throws AccountNotFoundException {
        Account account = accountService.update(id, name);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) throws AccountNotFoundException {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber)
            throws AccountNotFoundException {
        Account account = accountService.getByNumber(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}