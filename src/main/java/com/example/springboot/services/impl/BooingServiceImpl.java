package com.example.springboot.services.impl;

import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.repositories.BookingRepository;
import com.example.springboot.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BooingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getById(int id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> getByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<Car> selCarsByDateBooking(LocalDate start, LocalDate end) {
        return bookingRepository.selCarsByDateBooking(start, end);
    }

    @Override
    public void insBooking(Booking booking) {
        bookingRepository.saveAndFlush(booking);
    }

    @Override
    public void delBooking(Booking booking) {
        bookingRepository.delete(booking);
    }
}
