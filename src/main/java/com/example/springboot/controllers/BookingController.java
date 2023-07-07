package com.example.springboot.controllers;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.exceptions.BadRequestException;
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
    public void insertBooking(@RequestBody BookingDTO request) throws ItemNotFoundException, BadRequestException {
        bookingService.newBooking(request);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editBooking(@RequestBody BookingDTO request) throws ItemNotFoundException {
        bookingService.editBooking(request);
    }
    @PostMapping("detail/{status}")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatusBooking(@PathVariable("status") String status,
                                    @RequestBody BookingDTO request) throws ItemNotFoundException {
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
