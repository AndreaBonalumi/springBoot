package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long idCar;
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String description;
    private String link;
    private Integer year;
    private Long createdBy;
}
