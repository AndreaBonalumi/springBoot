package com.example.springboot.dto;

import com.example.springboot.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long idBooking;
    private LocalDate start;
    private LocalDate end;
    private Status status;
    private UserDTO user;
    private CarDTO car;
}
