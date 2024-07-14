package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.UserCreationDTO;
import com.banksolutions.ebank.dto.UserDTO;
import com.banksolutions.ebank.model.User;
import com.banksolutions.ebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserCreationDTO userCreationDTO) {
        if (userRepository.findByNationalId(userCreationDTO.getNationalId()).isPresent()) {
            throw new RuntimeException("User with this National ID already exists");
        }

        if (userRepository.findByEmail(userCreationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = getUser(userCreationDTO);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private static User getUser(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setNationalId(userCreationDTO.getNationalId());
        user.setFirstName(userCreationDTO.getFirstName());
        user.setLastName(userCreationDTO.getLastName());
        user.setEmail(userCreationDTO.getEmail());
        user.setPassword(userCreationDTO.getPassword());
        user.setPhone(userCreationDTO.getPhone());
        user.setAddress(userCreationDTO.getAddress());
        return user;
    }

    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(Long useId, UserDTO userDTO) {
        User user = userRepository.findById(useId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNationalId(user.getNationalId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        return dto;
    }
}