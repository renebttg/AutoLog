package com.example.autolog.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Rene
 */
@Entity
@Table(name = "TB_PARTS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idPart;

    @Column(nullable = false, unique = true, length = 50)
    private String partNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 255)
    private String description;
}
