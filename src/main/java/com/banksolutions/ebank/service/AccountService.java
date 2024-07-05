package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.AccountClosureDTO;
import com.banksolutions.ebank.dto.AccountCreationDTO;
import com.banksolutions.ebank.dto.AccountDTO;
import com.banksolutions.ebank.dto.TransactionDTO;
import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.model.Customer;
import com.banksolutions.ebank.model.Transaction;
import com.banksolutions.ebank.repository.AccountRepository;
import com.banksolutions.ebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final Random random = new Random();

    @Transactional
    public AccountDTO createAccount(AccountCreationDTO accountCreationDTO) {
        Customer customer = customerRepository.findById(accountCreationDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = new Account();
        account.setType(accountCreationDTO.getType());
        account.setBalance(accountCreationDTO.getInitialBalance());
        account.setCreationDate(LocalDate.now());
        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setClosed(false);

        Account savedAccount = accountRepository.save(account);
        return convertToDTO(savedAccount);
    }

    public AccountDTO getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return convertToDTO(account);
    }

    public List<TransactionDTO> getAccountTransactions(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getTransactions().stream()
                .map(this::convertToTransactionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO closeAccount(AccountClosureDTO accountClosureDTO) {
        Account account = accountRepository.findById(accountClosureDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Account balance must be zero before closing");
        }

        account.setClosed(true);
        account.setClosureReason(accountClosureDTO.getClosureReason());
        Account savedAccount = accountRepository.save(account);
        return convertToDTO(savedAccount);
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setCreationDate(account.getCreationDate());
        dto.setClosed(account.isClosed());
        return dto;
    }

    private TransactionDTO convertToTransactionDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setDate(transaction.getDate());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setDescription(transaction.getDescription());
        return dto;
    }

    private String generateAccountNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}