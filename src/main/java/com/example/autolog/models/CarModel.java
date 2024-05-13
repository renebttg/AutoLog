package com.example.autolog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rene
 */

@Entity
@Table(name = "TB_CARS")
public class CarModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;

    private String ownerName;
    private String carBrand;
    private String model;
    private String color;

    @Column(unique = true)
    private String licencePlate;

    @Column(unique = true)
    private String chassisNumber;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MaintenanceModel> maintenanceHistory;

    public long getIdCar() {
        return idCar;
    }

    public void setIdCar(long idCar) {
        this.idCar = idCar;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public List<MaintenanceModel> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public void setMaintenanceHistory(List<MaintenanceModel> maintenanceHistory) {
        this.maintenanceHistory = maintenanceHistory;
    }
}