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
@RequiredArgsConstructor
public class UserMapper {
    private final UserRepository userRepository;
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
        user.setCreatedBy(userRequest.getCreatedBy());

        if (userRequest.getIdUser() == null) {
            user.setCreated(LocalDate.now());
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
    public User responseToEntity(long id) throws ItemNotFoundException {
        return userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
    }
}
