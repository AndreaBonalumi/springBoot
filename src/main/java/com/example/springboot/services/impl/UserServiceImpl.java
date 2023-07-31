package com.example.springboot.services.impl;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getById(long id) throws ItemNotFoundException {

        User user = userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.userToUserDTO(user);
    }
    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream().map(userMapper::userToUserDTO).toList();
    }

    @Override
    public UserDTO getByUsername(String username) throws ItemNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public void checkUser(String username, String password) throws ItemNotFoundException {
        password = passwordEncoder.encode(password);

        userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ItemNotFoundException("password Errata"));
    }

    @Override
    public boolean checkAuth(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("Utente non trovato"));
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public UserDTO insUser(UserDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userMapper.userToUserDTO(userRepository.save(userMapper.userDTOToUser(userDTO)));
    }
    @Override
    public void delUser(long id) throws ItemNotFoundException {

        UserDTO user = getById(id);

        userRepository.deleteById(user.getIdUser());
    }
    @Override
    public UserDTO getRequestFromIdResponse(long id) {
        User user = userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.userToUserDTO(user);
    }
}
