package com.banking.bankingrest.repository;

import com.banking.bankingrest.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {

    @Query("SELECT a FROM Account a WHERE a.number IN :numbers AND a.name IN :names")
    List<Account> findByNumbersAndNames(List<String> numbers, List<String> names);

    Optional<Account> findByNumber(String number);
}
