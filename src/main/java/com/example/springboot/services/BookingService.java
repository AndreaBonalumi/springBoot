package com.example.springboot.services;

import com.example.springboot.dto.BookingRequest;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> getAll();
    Booking getById(int id) throws ItemNotFoundException;
    List<Booking> getByUser(User user);
    List<Car> selCarsByDateBooking(LocalDate start, LocalDate end);
    void insBooking(BookingRequest request) throws ItemNotFoundException;
    void delBooking(int id) throws ItemNotFoundException;
}
