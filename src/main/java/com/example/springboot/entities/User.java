package com.example.springboot.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @Basic(optional = false)
    private String username;
    @Basic(optional = false)
    private String firstName;
    @Basic(optional = false)
    private String lastName;
    @Basic(optional = false)
    private String password;
    @Basic(optional = false)
    private String created;
    @Basic(optional = false)
    private String bd;
    @Basic(optional = false)
    private boolean admin;
    @Basic(optional = false)
    private String email;
    @Basic
    private String nPatente;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Booking> bookings = new HashSet<>();

}
