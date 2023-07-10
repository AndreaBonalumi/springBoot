package com.example.springboot.controllers;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BookingService bookingService;
    private final UserMapper userMapper;
    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> allCars() {
        return userService.getAll();
    }
    @GetMapping("detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingsByUser (@PathVariable("id") long id) throws ItemNotFoundException {
        log.info("********** get prenotazioni dell'utente: " + id + " ************");

        UserDTO user = userService.getById(id);

        return bookingService.getByUser(userMapper.dtoToEntity(user));
    }
    @PostMapping(value = "insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUser(@RequestBody UserDTO request) {
        userService.insUser(request);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editUser(@RequestBody UserDTO request) {
        userService.insUser(request);
    }
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") long id) throws ItemNotFoundException {
        userService.delUser(id);
    }
}
