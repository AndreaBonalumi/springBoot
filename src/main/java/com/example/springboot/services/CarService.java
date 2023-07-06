package com.example.springboot.services;

import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface CarService {
    Car getById(long id) throws ItemNotFoundException;
    List<Car> getAll();
    Car getByPlate(String plate) throws ItemNotFoundException;
    void insCar(Car car);
    void delCar (Car car);
}
