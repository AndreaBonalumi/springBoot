package com.example.springboot.dto.mapper;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {
    public User newUser(UserDTO userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(user.getFirstName() + "." +
                userRequest.getLastName() + "@si2001.it");
        user.setAdmin(userRequest.isAdmin());
        user.setBd(userRequest.getBirthday());
        user.setNPatente(userRequest.getNPatente());
        user.setCreated(LocalDate.now());

        return user;
    }
    public UserDTO newUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setUsername(user.getUsername());
        userDTO.setAdmin(user.isAdmin());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(userDTO.getLastName());
        userDTO.setPassword(userDTO.getPassword());
        user.setEmail(user.getEmail());
        userDTO.setBirthday(user.getBd());
        userDTO.setNPatente(user.getNPatente());

        return userDTO;
    }
}
