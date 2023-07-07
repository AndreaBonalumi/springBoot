package com.example.springboot.dto.mapper;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking newBooking(User user, Car car, BookingDTO reservationRequest) {
        Booking booking = new Booking();

        if (reservationRequest.getIdBooking() != null) {
            booking.setIdBooking(reservationRequest.getIdBooking());
        }

        booking.setDateBookingEnd(reservationRequest.getEnd());
        booking.setDateBookingStart(reservationRequest.getStart());

        if (reservationRequest.getStatus() == null) {
            booking.setStatus(0);
        } else {
            booking.setStatus(booking.getStatus());
        }

        booking.setUser(user);
        booking.setCar(car);

        return booking;
    }
    public BookingDTO newBookingDTO (Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setIdBooking(booking.getIdBooking());
        bookingDTO.setStart(booking.getDateBookingStart());
        bookingDTO.setEnd(booking.getDateBookingEnd());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setUserId(booking.getUser().getIdUser());
        bookingDTO.setCarId(booking.getCar().getIdCar());

        return bookingDTO;
    }
}
