package com.example.springboot.controllers;

import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping(value = "all", produces = "application/json")
    public ResponseEntity<List<Car>> allCars() {
        log.info("***** otteniamo tutto *******");

        List<Car> cars = carService.getAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<List<Car>>(cars, HttpStatus.NO_CONTENT);
        }

        log.info("numero di auto : " + cars.size());
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    @GetMapping(value = "id/{plate}", produces = "application/json")
    public ResponseEntity<Car> carByPlate(@PathVariable("plate") String plate) throws ItemNotFoundException {
        log.info("********* otteniamo solo una auto con targa: " + plate + " ***********");

        Car car = carService.getByPlate(plate);

        if(car == null) {
            //return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);
            throw new ItemNotFoundException("auto assente o targa errata");
        }

        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }
    @PostMapping("insert")
    public ResponseEntity<Car> insertCar(@RequestBody Car car) {

        log.info("****** creazione auto con targa: " + car.getPlate() + " ************");

        if (carService.getByPlate(car.getPlate()) != null) {
            log.info("errore nel tipo di richiesta (POST)");
            return new ResponseEntity<Car>(HttpStatus.BAD_REQUEST);
        }

        carService.insCar(car);
        return new ResponseEntity<Car>(new HttpHeaders(), HttpStatus.CREATED);
    }
    @PutMapping("edit")
    public ResponseEntity<?> editCar(@RequestBody Car car) {
        log.info("****** modifica auto: " + car.getPlate() + " *********");

        if(carService.getByPlate(car.getPlate()) == null) {
            log.info("inserire una targa valida per la modifica");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        carService.insCar(car);
        return new ResponseEntity<Car>(new HttpHeaders(), HttpStatus.CREATED);

    }

    @DeleteMapping("delete/{plate}")
    public ResponseEntity<?> deleteCar(@PathVariable("plate") String plate) throws ItemNotFoundException {
        log.info("********** eliminazione auto di targa: " + plate + " **********");

        // per messaggi di fine metodo
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        Car car = carService.getByPlate(plate);

        if(car == null) {
            /*logger.info("auto: " + plate + " non trovata");

            return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);*/
            throw new ItemNotFoundException("auto non esistente o targa errata");
        }

        carService.delCar(car);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! restituisce un messaggio
        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "eliminazine auto: " + plate + " eseguita con successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
