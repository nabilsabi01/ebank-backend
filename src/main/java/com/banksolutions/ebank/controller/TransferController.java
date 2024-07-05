package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.dto.TransferDTO;
import com.banksolutions.ebank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferDTO> createTransfer(@RequestBody TransferDTO transferDTO) {
        return ResponseEntity.ok(transferService.createTransfer(transferDTO));
    }
}