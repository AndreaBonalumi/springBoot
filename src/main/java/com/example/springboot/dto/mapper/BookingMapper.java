package com.example.springboot.dto.mapper;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.entities.Booking;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.CarService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final UserService userService;
    private final CarService carService;
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    public Booking dtoToEntity(BookingDTO bookingDTO) throws ItemNotFoundException {
        Booking booking = new Booking();

        if (bookingDTO.getIdBooking() != null) {
            booking.setIdBooking(bookingDTO.getIdBooking());
        }

        booking.setDateBookingEnd(bookingDTO.getEnd());
        booking.setDateBookingStart(bookingDTO.getStart());

        if (bookingDTO.getStatus() == null) {
            booking.setStatus(0);
        } else {
            booking.setStatus(bookingDTO.getStatus());
        }

        booking.setUser(userMapper.dtoToEntity(this.userService.getById(bookingDTO.getUserId())));
        booking.setCar(carMapper.dtoToEntity((this.carService.getById(bookingDTO.getCarId()))));

        return booking;
    }
    public BookingDTO entityToDto(Booking booking) {
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
