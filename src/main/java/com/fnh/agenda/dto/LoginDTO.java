package com.fnh.agenda.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
 
