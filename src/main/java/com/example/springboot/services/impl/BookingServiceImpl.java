package com.example.springboot.services.impl;

import com.example.springboot.dto.BookingRequest;
import com.example.springboot.dto.mapper.BookingMapper;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.BookingRepository;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.CarService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CarService carService;
    private final BookingMapper bookingMapper;
    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }
    @Override
    public Booking getById(int id) throws ItemNotFoundException {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("prenotazione non trovata"));
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
    public void insBooking(BookingRequest request) throws ItemNotFoundException {
        User user = userService.getById(request.getUserId());
        Car car = carService.getById(request.getCarId());

        Booking booking = bookingMapper.newBooking(user, car, request);
        bookingRepository.saveAndFlush(booking);
    }
    @Override
    public void delBooking(int id) throws ItemNotFoundException {
        log.info("eliminazione Booking");

        Booking booking = getById(id);
        if (booking == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }
        bookingRepository.delete(booking);
    }
}
