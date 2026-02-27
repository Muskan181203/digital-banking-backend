package com.bank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bank.dto.AccountRequest;
import com.bank.dto.TransactionRequest;
import com.bank.dto.TransferRequest;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.exception.BankException;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger =
            LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public String transfer(TransferRequest request, String email) {

        logger.info("Transfer initiated from {} to {}",
                request.getFromAccount(), request.getToAccount());

        Account sender = accountRepository
                .findByAccountNumberAndUserEmail(request.getFromAccount(), email)
                .orElseThrow(() -> new BankException("Unauthorized access to sender account"));

        Account receiver = accountRepository
                .findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new BankException("Receiver account not found"));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BankException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction debit = Transaction.builder()
                .accountNumber(sender.getAccountNumber())
                .type("TRANSFER_DEBIT")
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();

        Transaction credit = Transaction.builder()
                .accountNumber(receiver.getAccountNumber())
                .type("TRANSFER_CREDIT")
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(debit);
        transactionRepository.save(credit);

        logger.info("Transfer successful");

        return "Transfer Successful";
    }

   

    @Override
    public String deposit(TransactionRequest request, String email) {

        Account account = accountRepository
                .findByAccountNumberAndUserEmail(request.getAccountNumber(), email)
                .orElseThrow(() -> new BankException("Unauthorized access to account"));

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .accountNumber(account.getAccountNumber())
                .type("DEPOSIT")
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return "Deposit Successful";
    }

    @Override
    public String withdraw(TransactionRequest request, String email) {

        Account account = accountRepository
                .findByAccountNumberAndUserEmail(request.getAccountNumber(), email)
                .orElseThrow(() -> new BankException("Unauthorized access to account"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BankException("Insufficient Balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .accountNumber(account.getAccountNumber())
                .type("WITHDRAW")
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return "Withdraw Successful";
    }

    @Override
    public String createAccount(AccountRequest request, String email) {

        logger.info("Creating account for user {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BankException("User not found"));

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString().substring(0, 10))
                .accountType(request.getAccountType())
                .balance(BigDecimal.ZERO)
                .user(user)
                .build();

        accountRepository.save(account);

        logger.info("Account created successfully with account number {}",
                account.getAccountNumber());

        return "Account Created Successfully";
    }
    @Override
    public List<Transaction> getTransactions(String accountNumber, String email) {

        accountRepository
                .findByAccountNumberAndUserEmail(accountNumber, email)
                .orElseThrow(() -> new BankException("Unauthorized access to account"));

        return transactionRepository.findByAccountNumber(accountNumber);
    }
}