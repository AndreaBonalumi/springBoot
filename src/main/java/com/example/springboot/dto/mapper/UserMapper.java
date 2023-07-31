package com.example.springboot.dto.mapper;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    User userDTOToUser(UserDTO userRequest);
    @Mapping(target = "password", ignore = true)
    UserDTO userToUserDTO(User user);
}
