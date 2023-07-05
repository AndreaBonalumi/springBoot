package com.example.springboot.controllers;

import com.example.springboot.entities.Car;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/car")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @GetMapping(value = "all", produces = "application/json")
    public ResponseEntity<List<Car>> allCars() {
        logger.info("***** otteniamo tutto *******");

        List<Car> cars = carService.findAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<List<Car>>(cars, HttpStatus.NO_CONTENT);
        }

        logger.info("numero di auto : " + cars.size());
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    @GetMapping(value = "id/{plate}", produces = "application/json")
    public ResponseEntity<Car> carByPlate(@PathVariable("plate") String plate) throws ItemNotFoundException {
        logger.info("********* otteniamo solo una auto con targa: " + plate + " ***********");

        Car car = carService.getByPlate(plate);

        if(car == null) {
            //return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);
            throw new ItemNotFoundException("auto assente o targa errata");
        }

        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }
    @PostMapping("insert")
    public ResponseEntity<Car> insertCar(@RequestBody Car car) {

        if (carService.getByPlate(car.getPlate()) == null) {
            logger.info("****** creazione auto con targa: " + car.getPlate() + " ************");
        } else {
            logger.info("******* modifichiamo l'auto di targa: " + car.getPlate() + " ************");
        }

        carService.insCar(car);
        return new ResponseEntity<Car>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{plate}")
    public ResponseEntity<?> deleteCar(@PathVariable("plate") String plate) throws ItemNotFoundException {
        logger.info("********** eliminazione auto di targa: " + plate + " **********");

        // per messaggi di completamento
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
