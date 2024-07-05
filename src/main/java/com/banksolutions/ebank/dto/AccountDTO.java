package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.AccountType;
import com.banksolutions.ebank.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private LocalDate creationDate;
    private boolean isClosed;
}



