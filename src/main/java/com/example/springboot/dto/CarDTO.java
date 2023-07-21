package com.example.springboot.dto;

import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[A-Za-z]{2}\\d{3}[A-Za-z]{2}", message = "targa errata")
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String description;
    private String link;
    private Integer year;
    private Long createdBy;
}
