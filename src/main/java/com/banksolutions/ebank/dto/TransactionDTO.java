package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private LocalDateTime date;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private Long accountId;
}