package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transferDate;
    private TransferType type;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Long beneficiaryId;
}