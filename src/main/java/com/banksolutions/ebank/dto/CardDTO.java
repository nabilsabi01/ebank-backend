package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.CardStatus;
import com.banksolutions.ebank.enums.CardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDTO {
    private Long id;
    private String cardNumber;
    private String expirationDate;
    private CardType type;
    private CardStatus status;
    private String blockReason;
    private Long accountId;
}