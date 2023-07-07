package com.example.springboot.services;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface CarService {
    CarDTO getById(long id) throws ItemNotFoundException;
    List<CarDTO> getAll();
    CarDTO getByPlate(String plate) throws ItemNotFoundException;
    void insCar(CarDTO car);
    void editCar(CarDTO car) throws ItemNotFoundException;
    void newCar(CarDTO car) throws BadRequestException;
    void delCar (long id) throws ItemNotFoundException;
}
