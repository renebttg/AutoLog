package com.example.autolog.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @author Rene
 */
@Entity
@Table(name = "TB_PARTS")
public class PartsModel {

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

    public long getIdPart() {
        return idPart;
    }

    public void setIdPart(long idPart) {
        this.idPart = idPart;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
