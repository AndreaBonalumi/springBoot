package com.example.springboot.dto.mapper;

import com.example.springboot.dto.UserRequest;
import com.example.springboot.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {
    public User newUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getUsername());
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
}
