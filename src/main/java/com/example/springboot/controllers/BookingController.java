package com.example.springboot.controllers;

import com.example.springboot.Status;
import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.CarDTO;
import com.example.springboot.dto.UserResponse;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO getBooking(@PathVariable("id") long id,
                                 @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException, BadRequestException {

        BookingDTO bookingDTO = bookingService.getById(id);
        UserResponse userResponse = userService.getById(bookingDTO.getUserId());

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(userResponse.getUsername())) {
            return bookingDTO;
        }
        else {
            throw new BadRequestException("non hai le autorizzazioni per accedere");
        }

    }
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertBooking(@RequestBody BookingDTO request,
                              @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException, BadRequestException {

        UserResponse userResponse = userService.getById(request.getUserId());

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(userResponse.getUsername())) {
            request.setStatus(Status.ToAPPROVE);

            bookingService.insBooking(request);
        }
        else {
            throw new BadRequestException("non hai le autorizzazioni per accedere");
        }

    }
    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> everyBooking() {
        return bookingService.getAll();
    }
    @PostMapping("manage/{status}")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatusBooking(@PathVariable("status") String status,
                                    @RequestBody BookingDTO request) throws ItemNotFoundException {
        log.info("approvazione o rifiuto della prenotazione");

        if (request.getIdBooking() == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        if (status.equals("approve")) {
            request.setStatus(Status.APPROVED);
        } else if (status.equals("decline")) {
            request.setStatus(Status.DECLINED);
        } else {
            log.info("modifica di stato non riconosciuta");
            throw new ItemNotFoundException("modifica di stato non riconosciuta");
        }

        bookingService.insBooking(request);
    }
    @GetMapping("byDate")
    @ResponseStatus(HttpStatus.OK)
    public List<CarDTO> getAvailableCars(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return bookingService.selCarsByDateBooking(start, end);
    }
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBooking(@PathVariable("id") long id,
                              @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException, BadRequestException {

        BookingDTO bookingDTO = bookingService.getById(id);
        UserResponse userResponse = userService.getById(bookingDTO.getUserId());

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(userResponse.getUsername())) {
            bookingService.delBooking(id);
        }
        else {
            throw new BadRequestException("non hai le autorizzazioni per accedere");
        }
    }
}
