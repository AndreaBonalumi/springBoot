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
    public User requestToEntity(UserRequest useruserRequest) {
        User user = new User();

        if (useruserRequest.getIdUser() != null) {
            user.setIdUser(useruserRequest.getIdUser());
        }

        user.setUsername(useruserRequest.getUsername());
        user.setPassword(useruserRequest.getPassword());
        user.setFirstName(useruserRequest.getFirstName());
        user.setLastName(useruserRequest.getLastName());
        user.setEmail(useruserRequest.getFirstName().trim() + "." +
                useruserRequest.getLastName().trim() + "@si2001.it");
        user.setAdmin(useruserRequest.isAdmin());
        user.setBd(useruserRequest.getBirthday());
        user.setDrivingLicense(useruserRequest.getDrivingLicense());

        if (useruserRequest.getIdUser() == null) {
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

        return userResponse;
    }
    public User responseToEntity(long id) throws ItemNotFoundException {
        return userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
    }
}
