package com.banksolutions.ebank.dto;

import lombok.Data;

@Data
public class UserCreationDTO {
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
}
