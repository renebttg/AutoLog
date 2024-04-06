package com.example.autolog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Rene
 */

@Entity
@Table(name = "tb_maintenance")
public class MaintenanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMaintenance;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private CarModel car;

    private String serviceDescription;
    private String serviceStatus;
    private LocalDate repairDate;
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

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public LocalDate getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDate repairDate) {
        this.repairDate = repairDate;
    }

    public BigDecimal getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(BigDecimal serviceValue) {
        this.serviceValue = serviceValue;
    }
}
