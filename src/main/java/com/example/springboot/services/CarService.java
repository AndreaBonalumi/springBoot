package com.example.springboot.services;

import com.example.springboot.entities.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAll();
    Car getByPlate(String plate);
}
