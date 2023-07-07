package com.example.springboot.controllers;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping(value = "all", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Car> allCars() {
        log.info("***** otteniamo tutto *******");

        List<Car> cars = carService.getAll();

        log.info("numero di auto : " + cars.size());
        return cars;
    }

    @GetMapping(value = "id/{plate}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO carByPlate(@PathVariable("plate") String plate) throws ItemNotFoundException {
        return carService.getByPlate(plate);
    }
    @PostMapping("insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCar(@RequestBody CarDTO car) throws ItemNotFoundException, BadRequestException {

        log.info("****** creazione auto con targa: " + car.getPlate() + " ************");

        if (carService.getById(car.getIdCar()) != null) {
            throw new BadRequestException("errore nel tipo di richiesta (POST)");
        }

        carService.insCar(car);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editCar(@RequestBody CarDTO car) throws ItemNotFoundException {
        log.info("****** modifica auto: " + car.getPlate() + " *********");
        carService.getById(car.getIdCar());
        carService.insCar(car);
    }

    @DeleteMapping("delete/{plate}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable("plate") String plate) throws ItemNotFoundException {
        log.info("********** eliminazione auto di targa: " + plate + " **********");
        CarDTO car = carService.getByPlate(plate);

        carService.delCar(car);
    }
}
