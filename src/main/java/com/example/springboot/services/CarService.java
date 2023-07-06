package com.example.springboot.services;

import com.example.springboot.entities.Car;

import java.util.List;

public interface CarService {
    Car getById(long id);
    List<Car> getAll();
    Car getByPlate(String plate);
    void insCar(Car car);
    void delCar (Car car);
}
