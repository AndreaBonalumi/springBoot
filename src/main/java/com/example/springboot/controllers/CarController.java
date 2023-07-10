package com.example.springboot.controllers;

import com.example.springboot.dto.CarDTO;
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
    @PostMapping("insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCar(@RequestBody CarDTO car) {
        carService.insCar(car);
    }
    @PutMapping("edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void editCar(@RequestBody CarDTO car) {
        carService.insCar(car);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable("id") long id) throws ItemNotFoundException {
        carService.delCar(id);
    }
}
