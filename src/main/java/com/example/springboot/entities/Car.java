package com.example.springboot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCar;
    @Column(nullable = false, unique = true)
    private String plate;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String color;
    private int year;
    private LocalDate created;
    @Column(nullable = false)
    private String description;
    private String link;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = true)
    private Set<Booking> bookings = new HashSet<>();
}
