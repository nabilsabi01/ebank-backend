package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.CardCreationDTO;
import com.banksolutions.ebank.dto.CardDTO;
import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.model.Card;
import com.banksolutions.ebank.enums.CardStatus;
import com.banksolutions.ebank.repository.AccountRepository;
import com.banksolutions.ebank.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.banksolutions.ebank.util.CardUtils.generateCardNumber;
import static com.banksolutions.ebank.util.CardUtils.generateExpirationDate;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public CardDTO createCard(CardCreationDTO cardCreationDTO) {
        Account account = accountRepository.findById(cardCreationDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Card card = new Card();
        card.setCardNumber(generateCardNumber());
        card.setExpirationDate(generateExpirationDate(5));
        card.setType(cardCreationDTO.getType());
        card.setStatus(CardStatus.INACTIVE);
        card.setAccount(account);

        Card savedCard = cardRepository.save(card);
        return convertToDTO(savedCard);
    }

    public CardDTO getCardInfo(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        return convertToDTO(card);
    }

    @Transactional
    public void activateDeactivateCard(Long id, boolean activate) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setStatus(activate ? CardStatus.ACTIVE : CardStatus.INACTIVE);
        cardRepository.save(card);
    }

    @Transactional
    public void blockCard(Long id, String reason) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setStatus(CardStatus.BLOCKED);
        card.setBlockReason(reason);
        cardRepository.save(card);
    }

    private CardDTO convertToDTO(Card card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setExpirationDate(generateExpirationDate(5));
        dto.setType(card.getType());
        dto.setStatus(card.getStatus());
        dto.setBlockReason(card.getBlockReason());
        dto.setAccountId(card.getAccount().getId());
        return dto;
    }
}