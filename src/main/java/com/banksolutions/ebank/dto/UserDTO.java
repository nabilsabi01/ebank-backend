package com.banksolutions.ebank.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}

