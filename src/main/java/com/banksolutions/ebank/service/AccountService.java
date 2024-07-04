package com.banksolutions.ebank.service;

import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        account.setCreationDate(LocalDate.now());
        account.setIsClosed(false);
        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<Account> getCustomerAccounts(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    public Account closeAccount(Long id, String reason) {
        Account account = getAccount(id);
        if (account.getBalance() > 0) {
            throw new IllegalStateException("Account balance must be zero to close");
        }
        account.setIsClosed(true);
        account.setReasonClose(reason);
        return accountRepository.save(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account updateBalance(Long id, Double amount) {
        Account account = getAccount(id);
        account.setBalance(account.getBalance() + amount);
        return save(account);
    }
}
