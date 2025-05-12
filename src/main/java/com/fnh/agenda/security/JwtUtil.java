package com.fnh.agenda.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;  // Cambié esto de javax.annotation a jakarta.annotation

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    // Constructor para verificar la inyección de la propiedad jwt.secret
    public JwtUtil() {
        System.out.println("Secret Key: " + jwtSecret);  // Verifica si el valor se inyecta correctamente
    }

    // Método para crear el JWT
    public String generateJwtToken(org.springframework.security.core.Authentication auth) {
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String username = userPrincipal.getUsername();

        // Crear la clave de firma usando el secreto
        Key signingKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact();
    }

    // Método para obtener el nombre de usuario del token
    public String getUsernameFromJwtToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(jwtSecret.getBytes())  // Usar un array de bytes para la clave de firma
            .build();
        
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    // Método para validar el token
    public boolean validateJwtToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())  // Usar un array de bytes para la clave de firma
                .build();

            parser.parseClaimsJws(token);  // Esto lanzará una excepción si el token no es válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Puedes registrar un error si es necesario
            System.out.println("JWT no válido: " + e.getMessage());
        }
        return false;
    }

    // Método para verificar la inyección de las propiedades
    @PostConstruct
    public void init() {
        System.out.println("jwt.secret: " + jwtSecret);  // Esto te ayudará a verificar si el valor es correcto.
    }
}
