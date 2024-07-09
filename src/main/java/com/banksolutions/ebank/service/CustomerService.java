package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.CustomerCreationDTO;
import com.banksolutions.ebank.dto.CustomerDTO;
import com.banksolutions.ebank.model.Customer;
import com.banksolutions.ebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public CustomerDTO createCustomer(CustomerCreationDTO customerCreationDTO) {
        if (customerRepository.findByNationalId(customerCreationDTO.getNationalId()).isPresent()) {
            throw new RuntimeException("Customer with this National ID already exists");
        }

        if (customerRepository.findByEmail(customerCreationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Customer with this email already exists");
        }

        Customer customer = getCustomer(customerCreationDTO);

        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    private static Customer getCustomer(CustomerCreationDTO customerCreationDTO) {
        Customer customer = new Customer();
        customer.setNationalId(customerCreationDTO.getNationalId());
        customer.setFirstName(customerCreationDTO.getFirstName());
        customer.setLastName(customerCreationDTO.getLastName());
        customer.setBirthDate(customerCreationDTO.getBirthDate());
        customer.setEmail(customerCreationDTO.getEmail());
        customer.setPassword(customerCreationDTO.getPassword());
        customer.setPhone(customerCreationDTO.getPhone());
        customer.setAddress(customerCreationDTO.getAddress());
        customer.setGender(customerCreationDTO.getGender());
        return customer;
    }

    public CustomerDTO getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return convertToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setNationalId(customer.getNationalId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setBirthDate(customer.getBirthDate());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setGender(customer.getGender());
        return dto;
    }
}