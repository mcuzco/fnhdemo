package com.fnh.agenda.dto;

import jakarta.validation.constraints.*;

public class RegisterDTO {
    @NotBlank @Size(min = 3, max = 50)
    private String username;

    @NotBlank @Size(max = 100) @Email
    private String email;

    @NotBlank @Size(min = 6, max = 40)
    private String password;

    @NotNull
    private String rol;          // "ADMIN" o "EMPLEADO"

    @NotNull
    private String sucursal;     // "Portoviejo", "Chone", "Santo_Domingo"

    // Default constructor
    public RegisterDTO() {
    }

    // Parameterized constructor
    public RegisterDTO(String username, String email, String password, String rol, String sucursal) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.sucursal = sucursal;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rol='" + rol + '\'' +
                ", sucursal='" + sucursal + '\'' +
                '}';
    }
}
