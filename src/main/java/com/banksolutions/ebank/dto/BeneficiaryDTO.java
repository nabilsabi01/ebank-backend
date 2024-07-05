package com.banksolutions.ebank.dto;

import lombok.Data;

@Data
public class BeneficiaryDTO {
    private Long id;
    private String name;
    private String accountNumber;
    private String bankName;
    private Long accountId;
}
