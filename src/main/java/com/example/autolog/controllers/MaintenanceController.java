package com.example.autolog.controllers;

import com.example.autolog.dtos.MaintenanceDTO;
import com.example.autolog.models.CarModel;
import com.example.autolog.models.MaintenanceModel;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.CarRepository;
import com.example.autolog.repositories.MaintenanceRepository;
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
public class MaintenanceController {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Save maintenance", description = "Add maintenance record for a specific car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Maintenance record added successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found")
    })
    @PostMapping("/users/{userId}/cars/{carId}/maintenance")
    public ResponseEntity<Object> saveMaintenance(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid MaintenanceDTO maintenanceDTO) {
        var maintenanceModel = new MaintenanceModel();
        BeanUtils.copyProperties(maintenanceDTO, maintenanceModel);

        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        CarModel car = carOptional.get();
        maintenanceModel.setCar(car);

        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceRepository.save(maintenanceModel));
    }

    @Operation(summary = "Get all maintenance for a car", description = "Retrieve a list of all maintenance records for a specific car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance records retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found")
    })
    @GetMapping("/users/{userId}/cars/{carId}/maintenance")
    public ResponseEntity<Object> getAllMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        List<MaintenanceModel> maintenanceList = carOptional.get().getMaintenanceHistory();
        return ResponseEntity.ok(maintenanceList);
    }

    @Operation(summary = "Get maintenance for a car", description = "Retrieve details of a single maintenance record for a specific car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance record retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User, car, or maintenance record not found")
    })
    @GetMapping("/users/{userId}/cars/{carId}/maintenance/{maintenanceId}")
    public ResponseEntity<Object> getMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        Optional<MaintenanceModel> maintenanceOptional = maintenanceRepository.findById(maintenanceId);
        if (maintenanceOptional.isEmpty() || !maintenanceOptional.get().getCar().equals(carOptional.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance not Found");
        }

        return ResponseEntity.ok(maintenanceOptional.get());
    }

    @Operation(summary = "Update maintenance", description = "Update details of an existing maintenance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance record updated successfully"),
            @ApiResponse(responseCode = "404", description = "User, car, or maintenance record not found")
    })
    @PutMapping("/users/{userId}/cars/{carId}/maintenance/{maintenanceId}")
    public ResponseEntity<Object> updateMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId, @RequestBody @Valid MaintenanceDTO maintenanceDTO) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        Optional<MaintenanceModel> maintenanceOptional = maintenanceRepository.findById(maintenanceId);
        if (maintenanceOptional.isEmpty() || !maintenanceOptional.get().getCar().equals(carOptional.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance not Found");
        }

        MaintenanceModel existingMaintenance = maintenanceOptional.get();
        BeanUtils.copyProperties(maintenanceDTO, existingMaintenance, "idMaintenance", "car");

        MaintenanceModel updatedMaintenance = maintenanceRepository.save(existingMaintenance);

        return ResponseEntity.ok(updatedMaintenance);
    }

    @Operation(summary = "Delete maintenance", description = "Delete a maintenance record for a specific car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User, car, or maintenance record not found")
    })
    @DeleteMapping("/users/{userId}/cars/{carId}/maintenance/{maintenanceId}")
    public ResponseEntity<Object> deleteMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found.");
        }

        Optional<CarModel> carOptional = carRepository.findByUserAndIdCar(userOptional.get(), carId);
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not Found");
        }

        Optional<MaintenanceModel> maintenanceOptional = maintenanceRepository.findById(maintenanceId);
        if (maintenanceOptional.isEmpty() || !maintenanceOptional.get().getCar().equals(carOptional.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance not Found");
        }

        maintenanceRepository.delete(maintenanceOptional.get());
        return ResponseEntity.ok("Maintenance deleted successfully");
    }







}
