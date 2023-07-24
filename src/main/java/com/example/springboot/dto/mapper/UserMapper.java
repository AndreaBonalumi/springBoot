package com.example.springboot.dto.mapper;

import com.example.springboot.dto.UserRequest;
import com.example.springboot.dto.UserResponse;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {
    public User requestToEntity(UserRequest userRequest) {
        User user = new User();

        if (userRequest.getIdUser() != null) {
            user.setIdUser(userRequest.getIdUser());
        }

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getFirstName().trim() + "." +
                userRequest.getLastName().trim() + "@si2001.it");
        user.setAdmin(userRequest.isAdmin());
        user.setBd(userRequest.getBirthday());
        user.setDrivingLicense(userRequest.getDrivingLicense());

        if (userRequest.getIdUser() == null) {
            user.setCreated(LocalDate.now());
        } else {
            user.setCreatedBy(userRequest.getCreatedBy());
        }

        return user;
    }
    public UserResponse entityToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setIdUser(user.getIdUser());
        userResponse.setUsername(user.getUsername());
        userResponse.setAdmin(user.isAdmin());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setDrivingLicense(user.getDrivingLicense());
        userResponse.setCreatedBy(user.getCreatedBy());

        return userResponse;
    }

    public UserRequest entityToRequest(User user) {
        UserRequest userRequest = new UserRequest();
        userRequest.setIdUser(user.getIdUser());
        userRequest.setBirthday(user.getBd());
        userRequest.setAdmin(user.isAdmin());
        userRequest.setFirstName(user.getFirstName());
        userRequest.setUsername(user.getUsername());
        userRequest.setCreatedBy(user.getCreatedBy());
        userRequest.setPassword(user.getPassword());
        userRequest.setDrivingLicense(user.getDrivingLicense());
        userRequest.setLastName(user.getLastName());

        return userRequest;
    }
}
