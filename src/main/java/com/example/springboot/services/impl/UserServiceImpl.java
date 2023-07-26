package com.example.springboot.services.impl;

import com.example.springboot.dto.UserRequest;
import com.example.springboot.dto.UserResponse;
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
    public UserResponse getById(long id) throws ItemNotFoundException {

        User user = userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.entityToResponse(user);
    }
    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream().map(userMapper::entityToResponse).toList();
    }

    @Override
    public UserResponse getByUsername(String username) throws ItemNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.entityToResponse(user);
    }

    @Override
    public boolean checkAuth(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("Utente non trovato"));
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public UserResponse insUser(UserRequest userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userMapper.entityToResponse(userRepository.save(userMapper.requestToEntity(userDTO)));
    }
    @Override
    public void delUser(long id) throws ItemNotFoundException {

        UserResponse user = getById(id);

        userRepository.deleteById(user.getIdUser());
    }
    @Override
    public UserRequest getRequestFromIdResponse(long id) {
        User user = userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.entityToRequest(user);
    }
}
