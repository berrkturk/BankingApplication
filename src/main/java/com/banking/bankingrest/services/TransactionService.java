package com.banking.bankingrest.services;

import com.banking.bankingrest.exception.AccountNotFoundException;
import com.banking.bankingrest.exception.InsufficientBalanceException;
import com.banking.bankingrest.entity.Account;
import com.banking.bankingrest.entity.Transaction;
import com.banking.bankingrest.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    private final AccountService accountService;

    public TransactionService(TransactionRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public void transferMoney(String fromAccountNumber, String toAccountNumber, String name, BigDecimal amount)
            throws AccountNotFoundException, InsufficientBalanceException {
        Account from = accountService.getByNumber(fromAccountNumber);
        Account to = accountService.getByNumber(toAccountNumber);
        makeTransfer(from, to, amount);
        saveTransaction(fromAccountNumber, toAccountNumber, name, amount);
    }

    public List<Transaction> getTransactionHistoryByAccountNumber(String number) {
        return repository.findByFromAccountNumberOrToAccountNumber(number, number);
    }

    private void makeTransfer(Account from, Account to, BigDecimal amount) throws InsufficientBalanceException {
        withdraw(from, amount);
        deposit(to, amount);
    }

    private void withdraw(Account account, BigDecimal amount) throws InsufficientBalanceException {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (BigDecimal.ZERO.compareTo(newBalance) > 0) {
            throw new InsufficientBalanceException();
        }
        accountService.updateMoney(account, newBalance);
    }

    private void deposit(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().add(amount);
        accountService.updateMoney(account, newBalance);
    }

    private void saveTransaction(String fromAccountNumber, String toAccountNumber, String name, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setName(name);
        transaction.setFromAccountNumber(fromAccountNumber);
        transaction.setToAccountNumber(toAccountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        repository.save(transaction);
    }
}
