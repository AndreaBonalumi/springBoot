package com.example.springboot.services;

import com.example.springboot.entities.User;

import java.util.List;

public interface UserService {
    User getById(long id);
    List<User> getAll();
    User getByUsername(String username);
    User getByUsernameAndPassword(String username, String password);
    void insUser(User user);
    void delUser(User user);
    boolean thereIsAdmin();
    void saveNewAdmin();
}
