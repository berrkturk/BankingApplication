package com.banking.bankingrest.exception;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException() {
        super("Current balance is insufficient to make this transaction");
    }
}
