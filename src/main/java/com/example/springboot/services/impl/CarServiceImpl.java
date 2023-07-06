package com.example.springboot.services.impl;

import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.CarRepository;
import com.example.springboot.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Override
    public Car getById(long id) throws ItemNotFoundException {
        return carRepository.findByIdCar(id)
                .orElseThrow(() -> new ItemNotFoundException("auto non trovata"));
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }
    @Override
    public Car getByPlate(String plate) throws ItemNotFoundException {
        log.info("********* otteniamo solo una auto con targa: " + plate + " ***********");

        return carRepository.getByPlate(plate)
                .orElseThrow(() -> new ItemNotFoundException("auto non trovata"));
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
