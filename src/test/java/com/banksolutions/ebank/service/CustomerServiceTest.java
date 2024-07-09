package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.CustomerDTO;
import com.banksolutions.ebank.model.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    final String name = "nabil";
    @Test
    void getCustomer() {
        CustomerDTO customer = customerService.getCustomer(1L);
        assertEquals(name, customer.getFirstName());
    }
}