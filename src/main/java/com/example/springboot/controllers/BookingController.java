package com.example.springboot.controllers;

import com.example.springboot.dto.BookingRequest;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.dto.mapper.BookingMapper;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.CarService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final CarService carService;
    private final BookingMapper bookingMapper;
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertBooking(@RequestBody BookingRequest request) {
        log.info("inserimento prenotazione");

        if(request.getIdBooking() != null) {
            log.info("non è possibile effettuare la modifica in questa sezione");
            throw new RuntimeException("non è possibile effettuare la modifica in questa sezione");
        }
        User user = userService.getById(request.getUserId());
        Car car = carService.getById(request.getCarId());

        Booking booking = bookingMapper.newBooking(user, car, request);

        bookingService.insBooking(booking);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editBooking(@RequestBody Booking booking) throws ItemNotFoundException {
        log.info("modifica prenotazione");

        if (booking.getIdBooking() == 0) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        bookingService.insBooking(booking);
    }
    @PostMapping("detail/{id}/{status}")
    public ResponseEntity<?> changeStatusBooking(@PathVariable("status") String status,
                                                 @PathVariable("id") int idBooking) throws ItemNotFoundException {
        log.info("approvazione o rifiuto della prenotazione");

        Booking booking = bookingService.getById(idBooking);

        if (booking == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        if (status.equals("approve")) {
            booking.setStatus(1);
        } else if (status.equals("decline")) {
            booking.setStatus(2);
        } else {
            log.info("modifica di stato non riconosciuta");
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

        bookingService.insBooking(booking);
        return new ResponseEntity<Booking>(booking, HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") int id) throws ItemNotFoundException {
        log.info("eliminazione Booking");

        Booking booking = bookingService.getById(id);

        if (booking == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        bookingService.delBooking(booking);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
