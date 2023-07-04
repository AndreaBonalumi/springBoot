package com.example.springboot.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Car implements Serializable {
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String plate;
    @Basic(optional = false)
    private String brand;
    @Basic(optional = false)
    private String model;
    @Basic(optional = false)
    private String color;
    @Basic(optional = false)
    private int year;
    @Basic(optional = false)
    private LocalDate created;
    @Basic(optional = false)
    private String description;
    @Basic
    private String link;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = true)
    @JsonManagedReference
    private Set<Booking> bookings = new HashSet<>();
}
