package com.example.springboot.repositories;

import com.example.springboot.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    Car getByPlate(String plate);
}
