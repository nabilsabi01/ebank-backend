package com.banksolutions.ebank.controller;

import com.banksolutions.ebank.model.Beneficiary;
import com.banksolutions.ebank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {
    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping
    public ResponseEntity<Beneficiary> addBeneficiary(@RequestBody Beneficiary beneficiary) {
        return ResponseEntity.ok(beneficiaryService.addBeneficiary(beneficiary));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficiary> updateBeneficiary(@PathVariable Long id, @RequestBody Beneficiary beneficiaryDetails) {
        return ResponseEntity.ok(beneficiaryService.updateBeneficiary(id, beneficiaryDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Beneficiary> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiary> getBeneficiary(@PathVariable Long id) {
        return ResponseEntity.ok(beneficiaryService.getBeneficiary(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Beneficiary>> getAccountBeneficiaries(@PathVariable Long accountId) {
        return ResponseEntity.ok(beneficiaryService.getAccountBeneficiaries(accountId));
    }
}