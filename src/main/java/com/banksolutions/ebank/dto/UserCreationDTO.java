package com.banksolutions.ebank.dto;

import lombok.Data;

@Data
public class UserCreationDTO {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String mail;
    private String phone;
}
