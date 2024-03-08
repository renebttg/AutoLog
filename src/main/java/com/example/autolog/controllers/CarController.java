package com.example.autolog.controllers;

import com.example.autolog.dtos.CarRecordDto;
import com.example.autolog.models.CarModel;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.CarRepository;
import com.example.autolog.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Rene
 */

@RestController
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users/{userId}/cars")
    public ResponseEntity<Object> saveCar(@PathVariable Long userId, @RequestBody @Valid CarRecordDto carRecordDto) {
        var carModel = new CarModel();
        BeanUtils.copyProperties(carRecordDto, carModel);

        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }
        UserModel user = userOptional.get();
        carModel.setUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(carRepository.save(carModel));
    }

    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<Object> getAllCarsForUser(@PathVariable Long userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        List<CarModel> cars = carRepository.findByUser(userOptional.get());

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<Object> getCarByUserIdAndCarId(@PathVariable Long userId, @PathVariable Long carId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        return ResponseEntity.ok(carOptional.get());
    }

    @PutMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<Object> updateCar(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid CarRecordDto carRecordDto) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        CarModel existingCar = carOptional.get();
        BeanUtils.copyProperties(carRecordDto, existingCar, "idCar", "user", "maintenanceHistory");

        CarModel updatedCar = carRepository.save(existingCar);

        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<Object> deleteCar(@PathVariable Long userId, @PathVariable Long carId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        carRepository.delete(carOptional.get());
        return ResponseEntity.ok("Deleted Successfully");
    }

}

