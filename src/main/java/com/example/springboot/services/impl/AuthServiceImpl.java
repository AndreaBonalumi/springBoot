package com.example.springboot.services.impl;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entities.User;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public Optional<User> addUser(UserDTO user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setAdmin(false);
        return Optional.of(userRepository.save(newUser));
    }
}
