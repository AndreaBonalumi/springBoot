package com.example.springboot.services;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.exceptions.ItemNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDTO getById(long id) throws ItemNotFoundException;
    UserDetailsService userDetailsService();
    List<UserDTO> getAll();
    void insUser(UserDTO userDTO);
    void delUser(long id) throws ItemNotFoundException;
}
