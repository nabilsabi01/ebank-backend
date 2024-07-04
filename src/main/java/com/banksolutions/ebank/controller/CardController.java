package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.model.Card;
import com.banksolutions.ebank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCard(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Card>> getAccountCards(@PathVariable Long accountId) {
        return ResponseEntity.ok(cardService.getAccountCards(accountId));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Card> activateCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.activateCard(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Card> deactivateCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.deactivateCard(id));
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Card> blockCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.blockCard(id));
    }
}