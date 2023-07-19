package com.example.springboot.entities;

import com.example.springboot.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBooking;
    @Column(nullable = false)
    private LocalDate dateBookingStart;
    @Column(nullable = false)
    private LocalDate dateBookingEnd;
    @Column(nullable = false)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "idUser")
    private User user;
    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "idCar")
    private Car car;
}
