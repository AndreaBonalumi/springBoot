package com.example.springboot.repositories;

import com.example.springboot.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByIdCar(long id);
    Car getByPlate(String plate);
}
