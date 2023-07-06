package com.example.springboot.dto.mapper;

import com.example.springboot.dto.BookingRequest;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking newBooking(User user, Car car, BookingRequest reservationRequest) {
        Booking booking = new Booking();
        booking.setIdBooking(reservationRequest.getIdBooking());
        booking.setDateBookingEnd(reservationRequest.getEnd());
        booking.setDateBookingStart(reservationRequest.getStart());
        booking.setStatus(booking.getStatus());
        booking.setUser(user);
        booking.setCar(car);

        return booking;
    }
}
