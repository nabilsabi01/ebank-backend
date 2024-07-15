package com.banksolutions.ebank.mapper;


import com.banksolutions.ebank.dto.UserDto;
import com.banksolutions.ebank.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
    List<User> toUsers(List<UserDto> userDtos);
    List<UserDto> toUserDtos(List<User> users);
    User updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
