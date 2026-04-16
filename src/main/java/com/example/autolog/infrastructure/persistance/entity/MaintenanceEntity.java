package com.example.autolog.infrastructure.persistance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Rene
 */

@Entity
@Table(name = "tb_maintenance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceEntity implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMaintenance;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private VehicleEntity car;

    private String serviceDescription;
    private String serviceStatus;
    private LocalDate repairDate;
    private BigDecimal serviceValue;
}
