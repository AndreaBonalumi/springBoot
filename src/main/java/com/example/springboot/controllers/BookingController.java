package com.example.springboot.controllers;

import com.example.springboot.dto.BookingRequest;
import com.example.springboot.entities.Booking;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertBooking(@RequestBody BookingRequest request) throws ItemNotFoundException {
        log.info("inserimento/modifica prenotazione");

        if(request.getIdBooking() != null) {
            log.info("non è possibile effettuare la modifica in questa sezione");
            throw new ItemNotFoundException("non è possibile effettuare la modifica in questa sezione");
        }
        bookingService.insBooking(request);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editBooking(@RequestBody BookingRequest request) throws ItemNotFoundException {
        log.info("modifica prenotazione");

        if (request.getIdBooking() == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        bookingService.insBooking(request);
    }
    @PostMapping("detail/{status}")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatusBooking(@PathVariable("status") String status,
                                    @RequestBody BookingRequest request) throws ItemNotFoundException {
        log.info("approvazione o rifiuto della prenotazione");

        if (request == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        if (status.equals("approve")) {
            request.setStatus(1);
        } else if (status.equals("decline")) {
            request.setStatus(2);
        } else {
            log.info("modifica di stato non riconosciuta");
            throw new ItemNotFoundException("modifica di stato non riconosciuta");
        }

        bookingService.insBooking(request);
    }
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBooking(@PathVariable("id") int id) throws ItemNotFoundException {
        bookingService.delBooking(id);
    }
}
