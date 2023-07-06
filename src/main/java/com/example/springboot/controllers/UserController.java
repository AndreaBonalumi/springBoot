package com.example.springboot.controllers;

import com.example.springboot.dto.UserRequest;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BookingService bookingService;

    /*@GetMapping("profile")
    public ResponseEntity<User> profileUser() {

    }*/
    @GetMapping("detail/{username}")
    public ResponseEntity<List<Booking>> getBookingsByUser (@PathVariable("username") String username) throws ItemNotFoundException {
        log.info("********** get prenotazioni dell'utente: " + username + " ************");

        User user = userService.getByUsername(username);

        if (user == null) {
            throw new ItemNotFoundException("utente non trovato");
        }

        List<Booking> bookings = bookingService.getByUser(user);
        return new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
    }

    @PostMapping(value = "insertUser")
    public ResponseEntity<?> insertUser(@RequestBody User user) throws ItemNotFoundException {
        log.info("inserimento di un nuovo utente");

        userService.getByUsername(user.getUsername());

        userService.insUser(user);
        return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.CREATED);
    }
    @PutMapping("editUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void editUser(@RequestBody User user) {
        log.info("modifica di un utente");

        userService.insUser(user);
    }
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") long id) throws ItemNotFoundException {
        userService.delUser(id);
    }
}
