package com.banking.bankingrest.controller;

import com.banking.bankingrest.entity.Transaction;
import com.banking.bankingrest.exception.AccountNotFoundException;
import com.banking.bankingrest.exception.InsufficientBalanceException;
import com.banking.bankingrest.request.transaction.TransactionTransferRequest;
import com.banking.bankingrest.services.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Transaction API", tags = "Transaction")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@Validated @RequestBody TransactionTransferRequest request)
            throws AccountNotFoundException, InsufficientBalanceException {
        transactionService.transferMoney(request.fromAccountNumber(), request.toAccountNumber(), request.name(),
                request.amount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/account/{number}")
    public ResponseEntity<List<Transaction>> getTransactionHistoryByAccountNumber(@PathVariable String number) {
        List<Transaction> transactions = transactionService.getTransactionHistoryByAccountNumber(number);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}