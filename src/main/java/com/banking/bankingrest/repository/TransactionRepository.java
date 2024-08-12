package com.banking.bankingrest.repository;

import com.banking.bankingrest.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByFromAccountNumberOrToAccountNumber(String fromAccountNumber, String toAccountNumber);
}
