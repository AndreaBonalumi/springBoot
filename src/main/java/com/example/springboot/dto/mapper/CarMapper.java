package com.example.springboot.dto.mapper;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.entities.Car;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CarMapper {
    public Car newCar(CarDTO carRequest) {
        Car car = new Car();

        if (carRequest.getIdCar() != null) {
            car.setIdCar(carRequest.getIdCar());
        }

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
    public CarDTO newCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setIdCar(car.getIdCar());
        carDTO.setBrand(car.getBrand());
        carDTO.setModel(car.getModel());
        carDTO.setColor(car.getColor());
        carDTO.setDescription(car.getDescription());
        carDTO.setPlate(car.getPlate());
        carDTO.setLink(car.getLink());
        carDTO.setYear(car.getYear());

        return carDTO;
    }
}
