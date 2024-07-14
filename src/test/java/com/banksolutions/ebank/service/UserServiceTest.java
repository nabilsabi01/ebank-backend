package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    final String name = "nabil";
    @Test
    void geUser() {
        UserDTO user = userService.getUser(1L);
        assertEquals(name, user.getFirstName());
    }
}