package com.example.springboot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Booking implements Serializable {
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Basic(optional = false)
    private LocalDate dateBookingStart;
    @Basic(optional = false)
    private LocalDate dateBookingEnd;
    @Basic(optional = false)
    private int status;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(referencedColumnName = "username")
    @JsonBackReference
    private User user;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(referencedColumnName = "plate")
    @JsonBackReference
    private Car car;
}
