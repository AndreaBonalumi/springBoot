package com.example.springboot.controllers;

import com.example.springboot.entities.Booking;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("insertBooking")
    public ResponseEntity<Booking> insertBooking(@RequestBody Booking booking) {
        log.info("inserimento prenotazione");

        if(booking.getId() != 0) {
            log.info("non è possibile effettuare la modifica in questa sezione");
            return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
        }

        bookingService.insBooking(booking);
        return new ResponseEntity<Booking>(new HttpHeaders(), HttpStatus.CREATED);
    }
    @PutMapping("edit")
    public ResponseEntity<Booking> editBooking(@RequestBody Booking booking) throws ItemNotFoundException {
        log.info("modifica prenotazione");

        if (booking.getId() == 0) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        bookingService.insBooking(booking);
        return new ResponseEntity<Booking>(new HttpHeaders(), HttpStatus.CREATED);
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
