package com.example.springboot.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Car {
    @Id
    @Basic(optional = false)
    private String id;
    @Basic(optional = false)
    private String brand
    @Basic(optional = false)
    private String model
    @Basic(optional = false)
    private String color
    @Basic(optional = false)
    private String year
    @Basic(optional = false)
    private String created
    @Basic(optional = false)
    private String brand
}
