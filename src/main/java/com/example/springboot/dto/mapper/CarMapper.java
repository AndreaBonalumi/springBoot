package com.example.springboot.dto.mapper;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.entities.Car;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CarMapper {
    public Car newCar(CarDTO carRequest) {
        Car car = new Car();
        car.setPlate(carRequest.getPlate());
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setColor(carRequest.getColor());
        car.setDescription(carRequest.getDescription());
        car.setLink(carRequest.getLink());
        car.setYear(carRequest.getYear());
        car.setCreated(LocalDate.now());

        return car;
    }
}
