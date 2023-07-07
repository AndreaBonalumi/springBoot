package com.example.springboot.services;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<BookingDTO> getAll();
    BookingDTO getById(int id) throws ItemNotFoundException;
    List<BookingDTO> getByUser(User user);
    List<Car> selCarsByDateBooking(LocalDate start, LocalDate end);
    void insBooking(BookingDTO request) throws ItemNotFoundException;
    void editBooking(BookingDTO request) throws ItemNotFoundException;
    void newBooking(BookingDTO booking) throws BadRequestException, ItemNotFoundException;
    void delBooking(int id) throws ItemNotFoundException;
}
