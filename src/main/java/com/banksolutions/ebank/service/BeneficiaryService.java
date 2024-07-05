package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.BeneficiaryDTO;
import com.banksolutions.ebank.model.Account;
import com.banksolutions.ebank.model.Beneficiary;
import com.banksolutions.ebank.repository.AccountRepository;
import com.banksolutions.ebank.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeneficiaryService {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
    @Autowired
    private AccountRepository accountRepository;

    public BeneficiaryDTO addBeneficiary(BeneficiaryDTO beneficiaryDTO) {
        Account account = accountRepository.findById(beneficiaryDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setName(beneficiaryDTO.getName());
        beneficiary.setAccountNumber(beneficiaryDTO.getAccountNumber());
        beneficiary.setBankName(beneficiaryDTO.getBankName());
        beneficiary.setAccount(account);

        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
        return convertToDTO(savedBeneficiary);
    }

    public void updateBeneficiary(Long id, BeneficiaryDTO beneficiaryDTO) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
        beneficiary.setName(beneficiaryDTO.getName());
        beneficiary.setAccountNumber(beneficiaryDTO.getAccountNumber());
        beneficiary.setBankName(beneficiaryDTO.getBankName());
        beneficiaryRepository.save(beneficiary);
    }

    public void deleteBeneficiary(Long id) {
        beneficiaryRepository.deleteById(id);
    }

    private BeneficiaryDTO convertToDTO(Beneficiary beneficiary) {
        BeneficiaryDTO dto = new BeneficiaryDTO();
        dto.setId(beneficiary.getId());
        dto.setName(beneficiary.getName());
        dto.setAccountNumber(beneficiary.getAccountNumber());
        dto.setBankName(beneficiary.getBankName());
        dto.setAccountId(beneficiary.getAccount().getId());
        return dto;
    }
}