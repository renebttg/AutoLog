package com.example.autolog.controllers;

import com.example.autolog.dtos.CarRecordDTO;
import com.example.autolog.exceptions.CarNotFoundException;
import com.example.autolog.exceptions.UserNotFoundException;
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
    public ResponseEntity<Object> saveCar(@PathVariable Long userId, @RequestBody @Valid CarRecordDTO carRecordDto) {
        var carModel = new CarModel();
        BeanUtils.copyProperties(carRecordDto, carModel);

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        carModel.setUser(user);

        CarModel savedCar = carRepository.save(carModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<Object> getAllCarsForUser(@PathVariable Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        List<CarModel> cars = carRepository.findByUser(user);

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<CarModel> getCarByUserIdAndCarId(@PathVariable Long userId, @PathVariable Long carId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        return ResponseEntity.ok(car);
    }

    @PutMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<Object> updateCar(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid CarRecordDTO carRecordDto) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel existingCar = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        BeanUtils.copyProperties(carRecordDto, existingCar, "idCar", "user", "maintenanceHistory");

        CarModel updatedCar = carRepository.save(existingCar);

        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<Object> deleteCar(@PathVariable Long userId, @PathVariable Long carId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        carRepository.delete(car);
        return ResponseEntity.ok("Car Deleted Successfully");
    }

}

