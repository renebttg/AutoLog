package com.example.autolog.models;

import com.example.autolog.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Rene
 */

@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable, UserDetails {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUser;
    private String name;
    @Column(unique = true)
    private String cnpj;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String nameWorkshop;
    private String addressWorkshop;
    private UserRole role;

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
    public void setPassword(String password) {
        this.password = password;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}