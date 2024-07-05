package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.TransferDTO;
import com.banksolutions.ebank.enums.TransactionType;
import com.banksolutions.ebank.enums.TransferType;
import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.model.Beneficiary;
import com.banksolutions.ebank.model.Transaction;
import com.banksolutions.ebank.model.Transfer;
import com.banksolutions.ebank.repository.AccountRepository;
import com.banksolutions.ebank.repository.BeneficiaryRepository;
import com.banksolutions.ebank.repository.TransactionRepository;
import com.banksolutions.ebank.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransferDTO createTransfer(TransferDTO transferDTO) {
        Account sourceAccount = accountRepository.findById(transferDTO.getSourceAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        if (sourceAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        Transfer transfer = new Transfer();
        transfer.setAmount(transferDTO.getAmount());
        transfer.setDescription(transferDTO.getDescription());
        transfer.setTransferDate(LocalDateTime.now());
        transfer.setType(transferDTO.getType());
        transfer.setSourceAccount(sourceAccount);

        if (transferDTO.getType() == TransferType.INTERNAL) {
            Account destinationAccount = accountRepository.findById(transferDTO.getDestinationAccountId())
                    .orElseThrow(() -> new RuntimeException("Destination account not found"));
            transfer.setDestinationAccount(destinationAccount);

            destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.getAmount()));
            accountRepository.save(destinationAccount);

            createTransaction(destinationAccount, transferDTO.getAmount(), TransactionType.CREDIT, "Transfer received");
        } else {
            Beneficiary beneficiary = beneficiaryRepository.findById(transferDTO.getBeneficiaryId())
                    .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
            transfer.setBeneficiary(beneficiary);
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
        accountRepository.save(sourceAccount);

        createTransaction(sourceAccount, transferDTO.getAmount(), TransactionType.DEBIT, "Transfer sent");

        Transfer savedTransfer = transferRepository.save(transfer);
        return convertToDTO(savedTransfer);
    }

    private void createTransaction(Account account, BigDecimal amount, TransactionType type, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    private TransferDTO convertToDTO(Transfer transfer) {
        TransferDTO dto = new TransferDTO();
        dto.setId(transfer.getId());
        dto.setAmount(transfer.getAmount());
        dto.setDescription(transfer.getDescription());
        dto.setTransferDate(transfer.getTransferDate());
        dto.setType(transfer.getType());
        dto.setSourceAccountId(transfer.getSourceAccount().getId());
        if (transfer.getDestinationAccount() != null) {
            dto.setDestinationAccountId(transfer.getDestinationAccount().getId());
        }
        if (transfer.getBeneficiary() != null) {
            dto.setBeneficiaryId(transfer.getBeneficiary().getId());
        }
        return dto;
    }
}