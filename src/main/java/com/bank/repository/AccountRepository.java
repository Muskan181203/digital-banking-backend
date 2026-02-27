package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.entity.Account;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    

    Optional<Account> findByAccountNumberAndUserEmail(String accountNumber, String email);
}