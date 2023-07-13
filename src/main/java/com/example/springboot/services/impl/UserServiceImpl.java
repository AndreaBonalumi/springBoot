package com.example.springboot.services.impl;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Override
    public UserDTO getById(long id) throws ItemNotFoundException {

        User user = userRepository.findByIdUser(id)
                .orElseThrow(() -> new ItemNotFoundException("utente non trovato"));
        return userMapper.entityToDto(user);
    }
    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream().map(userMapper::entityToDto).toList();
    }
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user non trovato"));
            }
        };
    }
    @Override
    public void insUser(UserDTO userDTO) {
        userRepository.save(userMapper.dtoToEntity(userDTO));
    }
    @Override
    public void delUser(long id) throws ItemNotFoundException {
        log.info("eliminazione user");

        UserDTO user = getById(id);

        userRepository.delete(userMapper.dtoToEntity(user));
    }
}
