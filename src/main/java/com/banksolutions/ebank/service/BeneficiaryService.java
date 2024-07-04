package com.banksolutions.ebank.service;

import com.banksolutions.ebank.model.Beneficiary;
import com.banksolutions.ebank.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryService {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public Beneficiary addBeneficiary(Beneficiary beneficiary) {
        return beneficiaryRepository.save(beneficiary);
    }

    public Beneficiary updateBeneficiary(Long id, Beneficiary beneficiaryDetails) {
        Beneficiary beneficiary = getBeneficiary(id);
        beneficiary.setFullName(beneficiaryDetails.getFullName());
        beneficiary.setNumber(beneficiaryDetails.getNumber());
        beneficiary.setBankName(beneficiaryDetails.getBankName());
        return beneficiaryRepository.save(beneficiary);
    }

    public void deleteBeneficiary(Long id) {
        beneficiaryRepository.deleteById(id);
    }

    public Beneficiary getBeneficiary(Long id) {
        return beneficiaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
    }

    public List<Beneficiary> getAccountBeneficiaries(Long accountId) {
        return beneficiaryRepository.findByAccountId(accountId);
    }
}