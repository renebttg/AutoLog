package com.example.autolog.controllers;

import com.example.autolog.dtos.MaintenanceDTO;
import com.example.autolog.models.CarModel;
import com.example.autolog.models.MaintenanceModel;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.CarRepository;
import com.example.autolog.repositories.MaintenanceRepository;
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
public class MaintenanceController {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

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
