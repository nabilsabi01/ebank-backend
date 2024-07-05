package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.CardStatus;
import com.banksolutions.ebank.enums.CardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardCreationDTO {
    private CardType type;
    private Long accountId;
}
