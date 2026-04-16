package com.example.autolog.infrastructure.persistance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rene
 */

@Entity
@Table(name = "TB_CARS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "licencePlate"}),
        @UniqueConstraint(columnNames = {"user_id", "chassisNumber"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    private String ownerName;
    private String carBrand;
    private String model;
    private String color;
    private String licencePlate;
    private String chassisNumber;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MaintenanceEntity> maintenanceHistory;

}