package com.banking.bankingrest.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {
        super("Account not found");
    }
}
