package com.banksolutions.ebank.service;

import com.banksolutions.ebank.model.Card;
import com.banksolutions.ebank.repository.CardRepository;
import com.banksolutions.ebank.enums.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card getCard(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public List<Card> getAccountCards(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    public Card activateCard(Long id) {
        Card card = getCard(id);
        card.setStatus(CartStatus.ACTIVE);
        return cardRepository.save(card);
    }

    public Card deactivateCard(Long id) {
        Card card = getCard(id);
        card.setStatus(CartStatus.INACTIVE);
        return cardRepository.save(card);
    }

    public Card blockCard(Long id) {
        Card card = getCard(id);
        card.setIsBlocked(true);
        return cardRepository.save(card);
    }
}