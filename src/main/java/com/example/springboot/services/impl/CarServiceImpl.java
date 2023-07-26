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
        return carMapper.entityToDto(car);
    }

    @Override
    public List<CarDTO> getAll() {
        return carRepository.findAll()
                .stream().map(carMapper::entityToDto)
                .toList();
    }
    @Override
    public void insCar(CarDTO carDTO) {
        carRepository.save(carMapper.dtoToEntity(carDTO));
    }
    @Override
    public void delCar(long id) throws ItemNotFoundException {

        CarDTO car = getById(id);

        carRepository.delete(carMapper.dtoToEntity(car));
    }
}
