package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.dto.AccountCreationDTO;
import com.banksolutions.ebank.dto.AccountDTO;
import com.banksolutions.ebank.dto.CardCreationDTO;
import com.banksolutions.ebank.dto.CardDTO;
import com.banksolutions.ebank.model.Card;
import com.banksolutions.ebank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardInfo(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardInfo(id));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable Long id) {
        cardService.activateDeactivateCard(id, true);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCard(@PathVariable Long id) {
        cardService.activateDeactivateCard(id, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockCard(@PathVariable Long id, @RequestParam String reason) {
        cardService.blockCard(id, reason);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardCreationDTO cardCreationDTO) {
        CardDTO createdAccount = cardService.createCard(cardCreationDTO);
        return ResponseEntity.ok(createdAccount);
    }
}
