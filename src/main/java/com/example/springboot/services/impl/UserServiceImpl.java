package com.example.springboot.services.impl;

import com.example.springboot.entities.User;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getById(long id) {

        return userRepository.findByIdUser(id)
                .orElseThrow(() -> new RuntimeException("utente non trovato"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void insUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean thereIsAdmin() {
        return userRepository.countByAdmin(true) > 0;
    }

    @Override
    public void saveNewAdmin() {
        User user = new User();
        user.setAdmin(true);
        user.setUsername("admin");
        user.setPassword("admin1");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@si2001.it");
        user.setCreated(LocalDate.now());
        user.setBd(LocalDate.now());

        userRepository.saveAndFlush(user);
    }
}
