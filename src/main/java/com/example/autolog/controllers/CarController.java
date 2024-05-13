package com.example.autolog.controllers;

import com.example.autolog.dtos.CarRecordDTO;
import com.example.autolog.models.CarModel;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.CarRepository;
import com.example.autolog.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Save a car", description = "Add a new car for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car added successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/users/{userId}/cars")
    public ResponseEntity<Object> saveCar(@PathVariable Long userId, @RequestBody @Valid CarRecordDTO carRecordDto) {
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

    @Operation(summary = "Get all cars for a user", description = "Retrieve a list of all cars for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<Object> getAllCarsForUser(@PathVariable Long userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        List<CarModel> cars = carRepository.findByUser(userOptional.get());

        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Get a car by user ID and car ID", description = "Retrieve details of a single car by user ID and car ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found")
    })
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

    @Operation(summary = "Update a car", description = "Update details of an existing car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found")
    })
    @PutMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<Object> updateCar(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid CarRecordDTO carRecordDto) {
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

    @Operation(summary = "Delete a car", description = "Delete a car by user ID and car ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found")
    })
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

