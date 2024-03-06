package com.example.autolog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Rene
 */

@Entity
@Table(name = "maintenance_history")
public class MaintenanceHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMaintenance;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private CarModel car;

    private String serviceDescription;
    private LocalDate repairDate;
    private LocalDate lastRepairDate;
    private BigDecimal serviceValue;

    public Long getIdMaintenance() {
        return idMaintenance;
    }

    public void setIdMaintenance(Long idMaintenance) {
        this.idMaintenance = idMaintenance;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public LocalDate getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDate repairDate) {
        this.repairDate = repairDate;
    }

    public LocalDate getLastRepairDate() {
        return lastRepairDate;
    }

    public void setLastRepairDate(LocalDate lastRepairDate) {
        this.lastRepairDate = lastRepairDate;
    }

    public BigDecimal getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(BigDecimal serviceValue) {
        this.serviceValue = serviceValue;
    }
}
