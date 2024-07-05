package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.dto.BeneficiaryDTO;
import com.banksolutions.ebank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {
    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping
    public ResponseEntity<BeneficiaryDTO> addBeneficiary(@RequestBody BeneficiaryDTO beneficiaryDTO) {
        return ResponseEntity.ok(beneficiaryService.addBeneficiary(beneficiaryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBeneficiary(@PathVariable Long id, @RequestBody BeneficiaryDTO beneficiaryDTO) {
        beneficiaryService.updateBeneficiary(id, beneficiaryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.ok().build();
    }
}