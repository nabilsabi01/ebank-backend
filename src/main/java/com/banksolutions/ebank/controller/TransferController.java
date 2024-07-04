package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.model.Transfer;
import com.banksolutions.ebank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<Transfer> createTransfer(@RequestBody Transfer transfer) {
        return ResponseEntity.ok(transferService.createTransfer(transfer));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transfer>> getAccountTransfers(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getAccountTransfers(accountId));
    }
}