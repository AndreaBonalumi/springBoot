package com.example.springboot.services.impl;

import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
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
    public User getByUsername(String username) throws ItemNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) throws ItemNotFoundException {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ItemNotFoundException("username o password errati"));
    }

    @Override
    public void insUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delUser(long id) throws ItemNotFoundException {
        log.info("eliminazione user");

        User user = getById(id);

        if (user == null) {
            throw new ItemNotFoundException("utente non trovato");
        }
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
