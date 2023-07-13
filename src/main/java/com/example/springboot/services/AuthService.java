package com.example.springboot.services;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entities.User;

import java.util.Optional;

public interface AuthService {
    Optional<User> addUser(UserDTO user);
}
