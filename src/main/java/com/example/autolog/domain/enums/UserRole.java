package com.example.autolog.domain.enums;

public enum UserRole {

    ADMIN("admin"),
    OWNER("employee"),
    EMPLOYEE("employee");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
