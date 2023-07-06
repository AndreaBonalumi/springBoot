package com.example.springboot.services;

import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface UserService {
    User getById(long id);
    List<User> getAll();
    User getByUsername(String username) throws ItemNotFoundException;
    User getByUsernameAndPassword(String username, String password) throws ItemNotFoundException;
    void insUser(User user);
    void delUser(long id) throws ItemNotFoundException;
    boolean thereIsAdmin();
    void saveNewAdmin();
}
