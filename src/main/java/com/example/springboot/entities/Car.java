package com.example.springboot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[A-Z]{2}\\d{3}[A-Z]{2}", message = "targa errata")
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = true)
    private Set<Booking> bookings = new HashSet<>();
}
