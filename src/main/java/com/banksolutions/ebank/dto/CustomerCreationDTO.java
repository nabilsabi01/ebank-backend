package com.banksolutions.ebank.dto;

import com.banksolutions.ebank.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerCreationDTO {
    private String nationalId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Gender gender;
}
