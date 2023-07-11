package com.example.springboot.dto.mapper;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.entities.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    public Booking dtoToEntity(BookingDTO bookingDTO){
        Booking booking = new Booking();

        if (bookingDTO.getIdBooking() != null) {
            booking.setIdBooking(bookingDTO.getIdBooking());
        }

        booking.setDateBookingEnd(bookingDTO.getEnd());
        booking.setDateBookingStart(bookingDTO.getStart());
        booking.setStatus(bookingDTO.getStatus());
        booking.setUser(userMapper.dtoToEntity(bookingDTO.getUser()));
        booking.setCar(carMapper.dtoToEntity(bookingDTO.getCar()));

        return booking;
    }
    public BookingDTO entityToDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setIdBooking(booking.getIdBooking());
        bookingDTO.setStart(booking.getDateBookingStart());
        bookingDTO.setEnd(booking.getDateBookingEnd());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setUser(userMapper.entityToDto(booking.getUser()));
        bookingDTO.setCar(carMapper.entityToDto(booking.getCar()));

        return bookingDTO;
    }
}
