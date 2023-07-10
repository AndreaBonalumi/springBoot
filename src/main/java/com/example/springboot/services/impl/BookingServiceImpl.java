package com.example.springboot.services.impl;

import com.example.springboot.Status;
import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.CarDTO;
import com.example.springboot.dto.mapper.BookingMapper;
import com.example.springboot.dto.mapper.CarMapper;
import com.example.springboot.entities.Booking;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.repositories.BookingRepository;
import com.example.springboot.services.BookingService;
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
    private final BookingMapper bookingMapper;
    private final CarMapper carMapper;
    @Override
    public List<BookingDTO> getAll() {
        return bookingRepository.findAll()
                .stream().map(bookingMapper::entityToDto)
                .toList();
    }
    @Override
    public BookingDTO getById(long id) throws ItemNotFoundException {
        Booking booking = bookingRepository.findByIdBooking(id)
                .orElseThrow(() -> new ItemNotFoundException("prenotazione non trovata"));
        return bookingMapper.entityToDto(booking);
    }
    @Override
    public List<BookingDTO> getByUser(User user) {
        return bookingRepository.findByUser(user)
                .stream().map(bookingMapper::entityToDto
                )
                .toList();
    }
    @Override
    public List<CarDTO> selCarsByDateBooking(LocalDate start, LocalDate end) {
        return bookingRepository.selCarsByDateBooking(start, end)
                .stream().map(carMapper::entityToDto).toList();
    }
    @Override
    public void insBooking(BookingDTO bookingDTO) throws ItemNotFoundException {
        log.info("inserimento/modifica prenotazione");
        bookingDTO.setStatus(Status.ToAPPROVE);

        Booking booking = bookingMapper.dtoToEntity(bookingDTO);

        bookingRepository.save(booking);
    }

    @Override
    public void delBooking(long id) throws ItemNotFoundException {
        log.info("eliminazione Booking");

        getById(id);

        bookingRepository.deleteById(id);
    }
}
