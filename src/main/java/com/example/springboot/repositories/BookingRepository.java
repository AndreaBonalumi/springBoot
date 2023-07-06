package com.example.springboot.repositories;

import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser(User user);
    Optional<Booking> findById(int id) throws ItemNotFoundException;
    @Query("select c from Car c where not exists (select b from Booking b where b.car = c and b.status != 2 and ((b.dateBookingStart between :start and :end) or (b.dateBookingEnd between :start and :end) or (:start between b.dateBookingStart and b.dateBookingEnd) or (:end between b.dateBookingStart and b.dateBookingEnd)))")
    List<Car> selCarsByDateBooking(LocalDate start, LocalDate end);
}