package com.example.springboot.controllers;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.UserRequest;
import com.example.springboot.dto.UserResponse;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.dto.security.AuthenticationRequest;
import com.example.springboot.dto.security.AuthenticationResponse;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.exceptions.NoAuthException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> allCars() {
        return userService.getAll();
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable("id") long id,
                                @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException {

        UserResponse userResponse = userService.getById(id);

        checkAuthorities(userResponse.getUsername(), userDetails);

        return userResponse;


    }
    @GetMapping("detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingsByUser (@PathVariable("id") long id,
                                               @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException {

        log.info("********** get prenotazioni dell'utente: " + id + " ************");

        UserResponse userResponse = userService.getById(id);

        checkAuthorities(userResponse.getUsername(), userDetails);

        return bookingService.getByUser(userMapper.responseToEntity(id));

    }
    @GetMapping("username")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getByUsername (@AuthenticationPrincipal UserDetails userDetails) throws ItemNotFoundException {
        return userService.getByUsername(userDetails.getUsername());
    }

    @PostMapping(value = "insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUser(@RequestBody UserRequest request,
                           @AuthenticationPrincipal UserDetails userDetails) {

        checkAuthorities(request.getUsername(), userDetails);

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
    private void checkAuthorities(String username,
                                  UserDetails userDetails) throws NoAuthException {
        if (!(userDetails.getAuthorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(username))) {
            throw new NoAuthException("non possiedi le autorizzazioni per effettuare questa operazione, riautenticati");
        }

    }
}
