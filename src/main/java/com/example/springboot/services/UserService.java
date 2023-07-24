package com.example.springboot.services;

import com.example.springboot.dto.UserRequest;
import com.example.springboot.dto.UserResponse;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface UserService {
    UserResponse getById(long id) throws ItemNotFoundException;
    List<UserResponse> getAll();
    UserResponse getByUsername(String username) throws ItemNotFoundException;
    void insUser(UserRequest userDTO);
    void delUser(long id) throws ItemNotFoundException;
    UserRequest getRequestFromResponse(long id);
}
