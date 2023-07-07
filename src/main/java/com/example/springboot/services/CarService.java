package com.example.springboot.services;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface CarService {
    CarDTO getById(long id) throws ItemNotFoundException;
    List<CarDTO> getAll();
    CarDTO getByPlate(String plate) throws ItemNotFoundException;
    void insCar(CarDTO car);
    void delCar (CarDTO car);
}
