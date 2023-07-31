package com.example.springboot.dto.mapper;

import com.example.springboot.dto.CarDTO;
import com.example.springboot.entities.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {
    CarDTO carToCarDTO(Car car);
    Car carDTOToCar(CarDTO carDTO);
}
