package com.example.autolog.presentation.controller;

import com.example.autolog.presentation.request.SaveMaintenanceRequest;
import com.example.autolog.infrastructure.service.MaintenanceService;
import jakarta.validation.Valid;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rene
 */

@RestController
@RequestMapping("/users/{userId}/cars/{carId}/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<Object> saveMaintenance(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid SaveMaintenanceRequest maintenanceDTO) {
        return maintenanceService.saveMaintenance(userId, carId, maintenanceDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getAllMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId) {
        return maintenanceService.getAllMaintenanceForCar(userId, carId);
    }

    @GetMapping("/{maintenanceId}")
    public ResponseEntity<Object> getMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        return maintenanceService.getMaintenanceForCar(userId, carId, maintenanceId);
    }

    @PutMapping("/{maintenanceId}")
    public ResponseEntity<Object> updateMaintenance(
            @PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId, @RequestBody @Valid SaveMaintenanceRequest maintenanceDTO) {
        return maintenanceService.updateMaintenance(userId, carId, maintenanceId, maintenanceDTO);
    }

    @DeleteMapping("/{maintenanceId}")
    public ResponseEntity<Object> deleteMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        return maintenanceService.deleteMaintenance(userId, carId, maintenanceId);
    }

}
