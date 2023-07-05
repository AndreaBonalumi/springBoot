package com.example.springboot.services;

import com.example.springboot.entities.Car;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Car getByPlate(String plate);
    void insCar(Car car);
    void delCar (Car car);
}
