package com.example.springboot.services.impl;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        return userMapper.newUserDto(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream().map(userMapper::newUserDto).toList();
    }

    @Override
    public UserDTO getByUsernameAndPassword(String username, String password) throws ItemNotFoundException {
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ItemNotFoundException("username o password errati"));
        return userMapper.newUserDto(user);
    }

    @Override
    public void insUser(UserDTO userDTO) {
        userRepository.save(userMapper.newUser(userDTO));
    }

    @Override
    public void editUser(UserDTO userDTO) throws ItemNotFoundException {
        log.info("modifica di un utente");

        if (userDTO.getIdUser() == null) {
            throw new ItemNotFoundException("user non trovato");
        }

        insUser(userDTO);
    }

    @Override
    public void newUser(UserDTO userDTO) throws BadRequestException {
        log.info("inserimento di un nuovo utente");

        if (userDTO.getIdUser() != null) {
            throw new BadRequestException("richiesta non supportata");
        }
        insUser(userDTO);
    }

    @Override
    public void delUser(long id) throws ItemNotFoundException {
        log.info("eliminazione user");

        UserDTO user = getById(id);

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
