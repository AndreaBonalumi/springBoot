package com.example.springboot.controllers;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.UserRequest;
import com.example.springboot.dto.UserResponse;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.dto.security.AuthenticationRequest;
import com.example.springboot.dto.security.AuthenticationResponse;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.security.config.JwtUtils;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.UserService;
import com.example.springboot.services.impl.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BookingService bookingService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtils jwtUtils;
    @GetMapping(value = "username")
    public String getUsername(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }
    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> allCars() {
        return userService.getAll();
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable("id") long id) throws ItemNotFoundException {
        return userService.getById(id);
    }
    @GetMapping("detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingsByUser (@PathVariable("id") long id) throws ItemNotFoundException {
        log.info("********** get prenotazioni dell'utente: " + id + " ************");

        return bookingService.getByUser(userMapper.responseToEntity(id));
    }
    @GetMapping("username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getByUsername (@PathVariable("username")String username) throws ItemNotFoundException {
        return userService.getByUsername(username);
    }
    @PostMapping(value = "insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUser(@RequestBody UserRequest request) {
        userService.insUser(request);
    }
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") long id) throws ItemNotFoundException {
        userService.delUser(id);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(),
                            new ArrayList<>()));
            final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getUsername());
            if (user != null) {
                String jwt = jwtUtils.generateToken(user);
                return ResponseEntity.ok(AuthenticationResponse.builder()
                        .jwt(jwt)
                        .build());
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }
}
