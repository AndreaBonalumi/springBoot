package com.example.springboot.services;

import com.example.springboot.entities.Car;
import com.example.springboot.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;
    @Override
    public List<Car> getAll() {
        Car car = new Car();
        car.setPlate("dy180af");
        car.setBrand("volswagen");
        car.setModel("polo");
        car.setColor("black");
        car.setCreated(LocalDate.now());
        carRepository.save(car);
        return carRepository.findAll();
    }

    @Override
    public Car getByPlate(String plate) {
        return carRepository.getByPlate(plate);
    }
}
