package com.example.springboot.services;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface CarService {
    CarDTO getById(long id) throws ItemNotFoundException;
    List<CarDTO> getAll();
    void insCar(CarDTO carDTO);
    void editCar(CarDTO carDTO) throws ItemNotFoundException;
    void newCar(CarDTO carDTO) throws BadRequestException;
    void delCar (long id) throws ItemNotFoundException;
}
