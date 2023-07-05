package com.example.springboot.services;

import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> getAll();
    Booking getById(int id);
    List<Booking> getByUser(User user);
    List<Car> selCarsByDateBooking(LocalDate start, LocalDate end);
    void insBooking(Booking booking);
    void delBooking(Booking booking);
}
