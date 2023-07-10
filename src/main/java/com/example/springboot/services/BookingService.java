package com.example.springboot.services;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.CarDTO;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<BookingDTO> getAll();
    BookingDTO getById(long id) throws ItemNotFoundException;
    List<BookingDTO> getByUser(User user);
    List<CarDTO> selCarsByDateBooking(LocalDate start, LocalDate end);
    void insBooking(BookingDTO bookingDTO) throws ItemNotFoundException;
    void editBooking(BookingDTO bookingDTO) throws ItemNotFoundException;
    void newBooking(BookingDTO bookingDTO) throws BadRequestException, ItemNotFoundException;
    void delBooking(long id) throws ItemNotFoundException;
}
