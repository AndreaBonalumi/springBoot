package com.example.springboot.services.impl;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.CarDTO;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.BookingMapper;
import com.example.springboot.dto.mapper.CarMapper;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.Car;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.BadRequestException;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.BookingRepository;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.CarService;
import com.example.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CarService carService;
    private final BookingMapper bookingMapper;
    private final CarMapper carMapper;
    private final UserMapper userMapper;
    @Override
    public List<BookingDTO> getAll() {
        return bookingRepository.findAll()
                .stream().map(bookingMapper::newBookingDTO)
                .toList();
    }
    @Override
    public BookingDTO getById(long id) throws ItemNotFoundException {
        Booking booking = bookingRepository.findByIdBooking(id)
                .orElseThrow(() -> new ItemNotFoundException("prenotazione non trovata"));
        return bookingMapper.newBookingDTO(booking);
    }
    @Override
    public List<BookingDTO> getByUser(User user) {
        return bookingRepository.findByUser(user)
                .stream().map(bookingMapper::newBookingDTO
                )
                .toList();
    }
    @Override
    public List<Car> selCarsByDateBooking(LocalDate start, LocalDate end) {
        return bookingRepository.selCarsByDateBooking(start, end);
    }
    @Override
    public void insBooking(BookingDTO bookingDTO) throws ItemNotFoundException {
        log.info("inserimento/modifica prenotazione");

        UserDTO user = userService.getById(bookingDTO.getUserId());
        CarDTO car = carService.getById(bookingDTO.getCarId());

        Booking booking = bookingMapper.newBooking(userMapper.newUser(user), carMapper.newCar(car), bookingDTO);

        bookingRepository.save(booking);
    }

    @Override
    public void editBooking(BookingDTO bookingDTO) throws ItemNotFoundException {
        log.info("modifica prenotazione");
        bookingDTO.setStatus(0);

        if (bookingDTO.getIdBooking() == null) {
            throw new ItemNotFoundException("prenotazione non trovata");
        }
        insBooking(bookingDTO);
    }

    @Override
    public void newBooking(BookingDTO bookingDTO) throws BadRequestException, ItemNotFoundException {
        log.info("inserimento prenotazione");

        if (bookingDTO.getIdBooking() != null) {
            throw new BadRequestException("tipo di richiesta non suportata");
        }
        insBooking(bookingDTO);
    }

    @Override
    public void delBooking(long id) throws ItemNotFoundException {
        log.info("eliminazione Booking");

        BookingDTO booking = getById(id);

        bookingRepository.delete(
                bookingMapper.newBooking(
                    userMapper.newUser(userService.getById(booking.getUserId())),
                    carMapper.newCar(carService.getById(booking.getCarId())),
                    booking));
    }
}
