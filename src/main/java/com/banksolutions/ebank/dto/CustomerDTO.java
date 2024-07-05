package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {
    private Long id;
    private String nationalId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String address;
    private Gender gender;
}

