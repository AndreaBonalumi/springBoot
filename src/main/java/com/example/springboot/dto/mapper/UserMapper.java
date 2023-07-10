package com.example.springboot.dto.mapper;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {
    public User dtoToEntity(UserDTO userDTO) {
        User user = new User();

        if (userDTO.getIdUser() != null) {
            user.setIdUser(userDTO.getIdUser());
        }

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getFirstName().trim() + "." +
                userDTO.getLastName().trim() + "@si2001.it");
        user.setAdmin(userDTO.isAdmin());
        user.setBd(userDTO.getBirthday());
        user.setDrivingLicense(userDTO.getDrivingLicense());

        if (userDTO.getIdUser() == null) {
            user.setCreated(LocalDate.now());
        }

        return user;
    }
    public UserDTO entityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setUsername(user.getUsername());
        userDTO.setAdmin(user.isAdmin());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthday(user.getBd());
        userDTO.setDrivingLicense(user.getDrivingLicense());

        return userDTO;
    }
}
