package com.example.springboot.services;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface UserService {
    UserDTO getById(long id) throws ItemNotFoundException;
    List<UserDTO> getAll();
    UserDTO getByUsername(String username) throws ItemNotFoundException;
    void checkUser(String username, String password) throws ItemNotFoundException;
    boolean checkAuth(String username, String password);
    UserDTO insUser(UserDTO userDTO);
    void delUser(long id) throws ItemNotFoundException;
    UserDTO getRequestFromIdResponse(long id);
}
