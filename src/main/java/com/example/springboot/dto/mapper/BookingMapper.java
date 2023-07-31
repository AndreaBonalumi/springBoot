package com.example.springboot.dto.mapper;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookingMapper {
    @Mapping(source = "dateBookingEnd", target = "end")
    @Mapping(source = "dateBookingStart", target = "start")
    @Mapping(source = "user.idUser", target = "userId")
    BookingDTO bookingToBookingDTO(Booking booking);

    @Mapping(source = "start", target = "dateBookingStart")
    @Mapping(source = "end", target = "dateBookingEnd")
    @Mapping(source = "userId", target = "user.idUser")
    Booking bookingDTOToBooking(BookingDTO bookingDTO);

}
