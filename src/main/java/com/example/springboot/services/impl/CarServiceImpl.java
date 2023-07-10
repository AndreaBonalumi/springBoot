package com.example.springboot.services.impl;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.dto.mapper.CarMapper;
import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.BadRequestException;
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
        log.info("***** otteniamo tutte le auto *******");

        return carRepository.findAll()
                .stream().map(carMapper::newCarDTO)
                .toList();
    }
    @Override
    public void insCar(CarDTO carDTO) {
        carRepository.save(carMapper.newCar(carDTO));
    }

    @Override
    public void editCar(CarDTO carDTO) throws ItemNotFoundException {
        log.info("****** modifica auto: " + carDTO.getPlate() + " *********");

        if (carDTO.getIdCar() == null) {
            throw new ItemNotFoundException("auto non trovata");
        }
        insCar(carDTO);
    }

    @Override
    public void newCar(CarDTO carDTO) throws BadRequestException {
        log.info("****** creazione auto con targa: " + carDTO.getPlate() + " ************");

        if (carDTO.getIdCar() != null) {
            throw new BadRequestException("tipo richiesta non supportata");
        }
        insCar(carDTO);
    }

    @Override
    public void delCar(long id) throws ItemNotFoundException {
        log.info("********** eliminazione auto : " + id + " **********");

        CarDTO car = getById(id);

        carRepository.delete(carMapper.newCar(car));
    }
}
