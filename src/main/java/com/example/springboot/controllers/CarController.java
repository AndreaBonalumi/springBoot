package com.example.springboot.controllers;

import com.example.springboot.dto.CarDTO;
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

    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<CarDTO> allCars() {
        return carService.getAll();
    }

    @GetMapping(value = "id/{plate}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO carByPlate(@PathVariable("plate") String plate) throws ItemNotFoundException {
        return carService.getByPlate(plate);
    }
    @PostMapping("insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCar(@RequestBody CarDTO car) throws BadRequestException {
        carService.newCar(car);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editCar(@RequestBody CarDTO car) throws ItemNotFoundException {
        carService.editCar(car);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable("id") long id) throws ItemNotFoundException {
        carService.delCar(id);
    }
}
