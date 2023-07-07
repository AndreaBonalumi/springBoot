package com.example.springboot.services;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface UserService {
    UserDTO getById(long id) throws ItemNotFoundException;
    List<UserDTO> getAll();
    UserDTO getByUsernameAndPassword(String username, String password) throws ItemNotFoundException;
    void insUser(UserDTO user);
    void editUser(UserDTO user) throws ItemNotFoundException;
    void newUser(UserDTO user) throws BadRequestException;
    void delUser(long id) throws ItemNotFoundException;
    boolean thereIsAdmin();
    void saveNewAdmin();
}
