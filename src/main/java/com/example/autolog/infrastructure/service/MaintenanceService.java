package com.example.autolog.infrastructure.service;

import com.example.autolog.presentation.request.SaveMaintenanceRequest;
import com.example.autolog.domain.exception.CarNotFoundException;
import com.example.autolog.domain.exception.MaintenanceNotFoundException;
import com.example.autolog.domain.exception.UserNotFoundException;
import com.example.autolog.infrastructure.persistance.entity.VehicleEntity;
import com.example.autolog.infrastructure.persistance.entity.MaintenanceEntity;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;
import com.example.autolog.domain.repository.CarRepository;
import com.example.autolog.domain.repository.MaintenanceRepository;
import com.example.autolog.domain.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Rene
 */
@Service
public class MaintenanceService {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> saveMaintenance(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid SaveMaintenanceRequest maintenanceDTO) {
        var maintenanceModel = new MaintenanceEntity();
        BeanUtils.copyProperties(maintenanceDTO, maintenanceModel);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        maintenanceModel.setCar(car);

        MaintenanceEntity savedMaintenance = maintenanceRepository.save(maintenanceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaintenance);
    }

    public ResponseEntity<Object> getAllMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        List<MaintenanceEntity> maintenanceList = car.getMaintenanceHistory();
        return ResponseEntity.ok(maintenanceList);
    }

    public ResponseEntity<Object> getMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        MaintenanceEntity maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance with ID " + maintenanceId + " not found"));

        return ResponseEntity.ok(maintenance);
    }

    public ResponseEntity<Object> updateMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId, @RequestBody @Valid SaveMaintenanceRequest maintenanceDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        MaintenanceEntity existingMaintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance with ID " + maintenanceId + " not found"));

        BeanUtils.copyProperties(maintenanceDTO, existingMaintenance, "idMaintenance", "car");

        MaintenanceEntity updatedMaintenance = maintenanceRepository.save(existingMaintenance);

        return ResponseEntity.ok(updatedMaintenance);
    }

    public ResponseEntity<Object> deleteMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        MaintenanceEntity maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance with ID " + maintenanceId + " not found"));

        maintenanceRepository.delete(maintenance);
        return ResponseEntity.ok("Maintenance deleted successfully");
    }

}
