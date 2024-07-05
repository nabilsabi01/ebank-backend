package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreationDTO {
    private AccountType type;
    private BigDecimal initialBalance;
    private Long customerId;
}
