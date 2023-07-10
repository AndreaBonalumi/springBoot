package com.example.springboot.services;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.exceptions.ItemNotFoundException;

import java.util.List;

public interface CarService {
    CarDTO getById(long id) throws ItemNotFoundException;
    List<CarDTO> getAll();
    void insCar(CarDTO carDTO);
    void delCar (long id) throws ItemNotFoundException;
}
