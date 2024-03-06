package com.example.autolog.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rene
 */

@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUser;
    private String name;

    @Column(unique = true)
    private String cnpj;

    @Column(unique = true)
    private String email;

    private String phone;
    private String nameWorkshop;
    private String addressWorkshop;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CarModel> cars;

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameWorkshop() {
        return nameWorkshop;
    }

    public void setNameWorkshop(String nameWorkshop) {
        this.nameWorkshop = nameWorkshop;
    }

    public String getAddressWorkshop() {
        return addressWorkshop;
    }

    public void setAddressWorkshop(String addressWorkshop) {
        this.addressWorkshop = addressWorkshop;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }
}