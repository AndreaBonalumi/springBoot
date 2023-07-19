package com.example.springboot.controllers;

import com.example.springboot.enums.Status;
import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.CarDTO;
import com.example.springboot.dto.UserResponse;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.exceptions.NoAuthException;
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
            throws ItemNotFoundException {

        BookingDTO bookingDTO = bookingService.getById(id);
        UserResponse userResponse = userService.getById(bookingDTO.getUserId());

        checkAuthorities(userResponse.getUsername(), userDetails);
        return bookingDTO;

    }
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertBooking(@RequestBody BookingDTO request,
                              @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException {

        UserResponse userResponse = userService.getById(request.getUserId());
        if (request.getStart().isAfter(request.getEnd())) {
            throw new BadRequestException("errore nell'inserimento delle date");
        }

        checkAuthorities(userResponse.getUsername(), userDetails);
        request.setStatus(Status.TOAPPROVE);

        bookingService.insBooking(request);

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

        status = status.toLowerCase();

        if (request.getIdBooking() == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }

        if (status.equals("approved")) {
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
            throws ItemNotFoundException {

        BookingDTO bookingDTO = bookingService.getById(id);
        UserResponse userResponse = userService.getById(bookingDTO.getUserId());

        checkAuthorities(userResponse.getUsername(), userDetails);

        bookingService.delBooking(id);

    }
    private void checkAuthorities(String username,
                                  UserDetails userDetails) throws NoAuthException {
        if (!(userDetails.getAuthorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(username))) {
            throw new NoAuthException("non possiedi le autorizzazioni per effettuare questa operazione, riautenticati");
        }

    }

}
