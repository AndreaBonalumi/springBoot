package com.example.springboot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Pattern(regexp = ".*\\d.*", message = "la password deve contenere almeno un numero")
    private String password;
    private LocalDate created;
    private LocalDate bd;
    @Column(nullable = false)
    private boolean admin;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private long createdBy;
    @Pattern(regexp = "[a-zA-Z]+", message = "la patente deve conenere solo lettere")
    private String drivingLicense;
    private String profilePhoto;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<Booking> bookings = new HashSet<>();
}
