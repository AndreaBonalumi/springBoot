package com.example.springboot.services.impl;

import com.example.springboot.entities.Car;
import com.example.springboot.repositories.CarRepository;
import com.example.springboot.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    @Override
    public Car getById(long id) {
        return carRepository.findByIdCar(id)
                .orElseThrow(() -> new RuntimeException("auto non trovata"));
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }
    @Override
    public Car getByPlate(String plate) {
        return carRepository.getByPlate(plate);
    }
    @Override
    public void insCar(Car car) {
        carRepository.saveAndFlush(car);
    }
    @Override
    public void delCar(Car car) {
        carRepository.delete(car);
    }
}
