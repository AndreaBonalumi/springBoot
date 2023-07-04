package com.example.springboot.controllers;

import com.example.springboot.entities.Car;
import com.example.springboot.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/car")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @GetMapping("all")
    public ResponseEntity<List<Car>> allCars() {
        logger.info("***** otteniamo tutte le auto *******");

        List<Car> cars = carService.getAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<List<Car>>(cars, HttpStatus.NO_CONTENT);
        }

        logger.info("numero di auto : " + cars.size());
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }
}
