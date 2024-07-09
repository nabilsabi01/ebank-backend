package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.dto.AccountClosureDTO;
import com.banksolutions.ebank.dto.AccountCreationDTO;
import com.banksolutions.ebank.dto.AccountDTO;
import com.banksolutions.ebank.dto.TransactionDTO;
import com.banksolutions.ebank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountCreationDTO accountCreationDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountCreationDTO);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long accountId) {
        AccountDTO account = accountService.getAccount(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getAccountTransactions(@PathVariable Long accountId) {
        List<TransactionDTO> transactions = accountService.getAccountTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/close")
    public ResponseEntity<AccountDTO> closeAccount(@RequestBody AccountClosureDTO accountClosureDTO) {
        AccountDTO closedAccount = accountService.closeAccount(accountClosureDTO);
        return ResponseEntity.ok(closedAccount);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) throws AccountNotFoundException {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}