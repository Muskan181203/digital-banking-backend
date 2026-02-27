package com.bank.service;

import java.util.List;

import com.bank.dto.AccountRequest;
import com.bank.dto.TransactionRequest;
import com.bank.dto.TransferRequest;
import com.bank.entity.Transaction;

public interface AccountService {

    String transfer(TransferRequest request, String email);

    List<Transaction> getTransactions(String accountNumber, String email);

    String createAccount(AccountRequest request, String email);

    String deposit(TransactionRequest request, String email);

    String withdraw(TransactionRequest request, String email);
}