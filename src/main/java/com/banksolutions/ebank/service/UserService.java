package com.banksolutions.ebank.service;

import com.banksolutions.ebank.dto.UserDto;
import com.banksolutions.ebank.exception.DatabaseEmptyException;
import com.banksolutions.ebank.exception.UserNotFoundException;
import com.banksolutions.ebank.mapper.UserMapper;
import com.banksolutions.ebank.model.User;
import com.banksolutions.ebank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<User> getAll() {
        var users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new DatabaseEmptyException();
        }
        return users;
    }

    public UserDto save(UserDto userDto) {
        var user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto update(UserDto userDto, Long id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        var userUpdated = userMapper.updateUserFromDto(userDto, user);
        return userMapper.toUserDto(userRepository.save(userUpdated));
    }

    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public UserDto delete(Long id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return userMapper.toUserDto(user);
    }
}
