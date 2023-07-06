package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
    private Long idCar;
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String description;
    private String link;
    private Integer year;
}
