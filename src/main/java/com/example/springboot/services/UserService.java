package com.example.springboot.services;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface UserService {
    UserDTO getById(long id);
    List<UserDTO> getAll();
    UserDTO getByUsername(String username) throws ItemNotFoundException;
    UserDTO getByUsernameAndPassword(String username, String password) throws ItemNotFoundException;
    void insUser(UserDTO user);
    void delUser(long id) throws ItemNotFoundException;
    boolean thereIsAdmin();
    void saveNewAdmin();
}
