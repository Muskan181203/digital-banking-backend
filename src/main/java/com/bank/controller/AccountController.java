package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.bank.dto.TransactionRequest;

import com.bank.dto.AccountRequest;
import com.bank.service.AccountService;
import java.util.List;
import com.bank.entity.Transaction;
import com.bank.dto.TransferRequest;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/deposit")
    public String deposit(@RequestBody TransactionRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.deposit(request, email);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.transfer(request, email);
    }
    @GetMapping("/transactions/{accountNumber}")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return accountService.getTransactions(accountNumber, email);
    }
    @PostMapping("/withdraw")
    public String withdraw(@RequestBody TransactionRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.withdraw(request, email);
    }
    @PostMapping("/create")
    public String createAccount(@RequestBody AccountRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return accountService.createAccount(request, email);
    }
}