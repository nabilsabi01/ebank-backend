package com.banksolutions.ebank.dto;

import lombok.Data;

@Data
public class AccountClosureDTO {
    private Long accountId;
    private String closureReason;
}
