package com.banksolutions.ebank.service;

import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.model.Transfer;
import com.banksolutions.ebank.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public Transfer createTransfer(Transfer transfer) {
        Account fromAccount = accountService.getAccount(transfer.getAccount().getId());
        if (fromAccount.getIsClosed()) {
            throw new IllegalStateException("Cannot transfer from a closed account");
        }

        double amount = Double.parseDouble(transfer.getAmount());
        if (amount <= 0 || fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountService.save(fromAccount);

        transfer.setTransferDate(LocalDateTime.now());
        return transferRepository.save(transfer);
    }

    public List<Transfer> getAccountTransfers(Long accountId) {
        return transferRepository.findByAccountId(accountId);
    }
}