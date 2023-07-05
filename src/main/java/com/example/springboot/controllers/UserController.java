package com.example.springboot.controllers;

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
@RequestMapping("user")
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

    @PostMapping("insertUser")
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        log.info("inserimento di un nuovo utente");

        if (userService.getByUsername(user.getUsername()) != null) {
            log.info("non è possibile effettuare la modifica in questa sezione");
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

        userService.insUser(user);
        return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.CREATED);
    }
    @PutMapping("editUser")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        log.info("modifica di un utente");

        if (userService.getByUsernameAndPassword(user.getUsername(), user.getPassword()) == null) {
            log.info("verifica il login prima della modifica");
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }

        userService.insUser(user);
        return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.CREATED);
    }
    @DeleteMapping("delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) throws ItemNotFoundException {
        log.info("eliminazione user");

        User user = userService.getByUsername(username);

        if (user == null) {
            throw new ItemNotFoundException("utente non trovato");
        }

        userService.delUser(user);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
