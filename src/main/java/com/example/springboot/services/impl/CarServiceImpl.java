package com.example.springboot.services.impl;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.dto.mapper.CarMapper;
import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.CarRepository;
import com.example.springboot.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    @Override
    public CarDTO getById(long id) throws ItemNotFoundException {
        Car car = carRepository.findByIdCar(id)
                .orElseThrow(() -> new ItemNotFoundException("auto non trovata"));
        return carMapper.newCarDTO(car);
    }

    @Override
    public List<CarDTO> getAll() {
        return carRepository.findAll()
                .stream().map(carMapper::newCarDTO)
                .toList();
    }
    @Override
    public CarDTO getByPlate(String plate) throws ItemNotFoundException {
        log.info("********* otteniamo solo una auto con targa: " + plate + " ***********");

        Car car = carRepository.getByPlate(plate)
                .orElseThrow(() -> new ItemNotFoundException("auto non trovata"));
        return carMapper.newCarDTO(car);
    }
    @Override
    public void insCar(CarDTO car) {
        carRepository.save(carMapper.newCar(car));
    }
    @Override
    public void delCar(CarDTO car) {
        carRepository.delete(carMapper.newCar(car));
    }
}
