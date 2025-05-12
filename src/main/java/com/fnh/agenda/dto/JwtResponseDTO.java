package com.fnh.agenda.dto;

public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String username;
    private String rol;
    private String sucursal;

    public JwtResponseDTO(String token, String username, String rol, String sucursal) {
        this.token = token;
        this.username = username;
        this.rol = rol;
        this.sucursal = sucursal;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public String getSucursal() {
        return sucursal;
    }
}
 
