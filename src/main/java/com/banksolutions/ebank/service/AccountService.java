package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.AccountClosureDTO;
import com.banksolutions.ebank.dto.AccountCreationDTO;
import com.banksolutions.ebank.dto.AccountDTO;
import com.banksolutions.ebank.dto.TransactionDTO;
import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.model.User;
import com.banksolutions.ebank.model.Transaction;
import com.banksolutions.ebank.repository.AccountRepository;
import com.banksolutions.ebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
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
    private UserRepository userRepository;

    @Autowired
    private CardService cardService;

    private final Random random = new Random();

    @Transactional
    public AccountDTO createAccount(AccountCreationDTO accountCreationDTO) {
        User user = userRepository.findById(accountCreationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setType(accountCreationDTO.getType());
        account.setBalance(accountCreationDTO.getInitialBalance());
        account.setCreationDate(LocalDate.now());
        account.setUser(user);
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

    @Transactional
    public void deleteAccount(Long accountId) throws AccountNotFoundException {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found with id: " + accountId);
        }
        accountRepository.deleteById(accountId);
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