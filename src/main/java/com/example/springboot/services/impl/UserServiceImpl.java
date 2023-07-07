package com.example.springboot.services.impl;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getById(long id) {

        User user = userRepository.findByIdUser(id)
                .orElseThrow(() -> new RuntimeException("utente non trovato"));
        return userMapper.newUserDto(user);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(userMapper.newUserDto(user));
        }
        return userDTOList;
    }

    @Override
    public UserDTO getByUsername(String username) throws ItemNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.newUserDto(user);
    }

    @Override
    public UserDTO getByUsernameAndPassword(String username, String password) throws ItemNotFoundException {
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ItemNotFoundException("username o password errati"));
        return userMapper.newUserDto(user);
    }

    @Override
    public void insUser(UserDTO user) {
        userRepository.save(userMapper.newUser(user));
    }

    @Override
    public void delUser(long id) throws ItemNotFoundException {
        log.info("eliminazione user");

        UserDTO user = getById(id);

        if (user == null) {
            throw new ItemNotFoundException("utente non trovato");
        }

        userRepository.delete(userMapper.newUser(user));
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
