package com.fnh.agenda.controller;

import com.fnh.agenda.dto.*;
import com.fnh.agenda.model.Usuario;
import com.fnh.agenda.repository.UsuarioRepository;
import com.fnh.agenda.security.JwtUtil;
import com.fnh.agenda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto, BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos de login inválidos");
        }
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        String token = jwtUtil.generateJwtToken(auth);
        org.springframework.security.core.userdetails.User uDetail =
            (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        Usuario user = usuarioRepo.findByUsername(uDetail.getUsername()).get();
        JwtResponseDTO resp = new JwtResponseDTO(
            token,
            user.getUsername(),
            user.getRol().name(),
            user.getSucursal().name()
        );
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto, BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos de registro inválidos");
        }
        if (usuarioRepo.existsByUsername(dto.getUsername())) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("El username ya existe");
        }
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("El email ya está en uso");
        }
        Usuario u = new Usuario();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setRol(com.fnh.agenda.model.Role.valueOf(dto.getRol()));
        u.setSucursal(com.fnh.agenda.model.Sucursal.valueOf(dto.getSucursal()));
        usuarioService.saveNewUser(u);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // En JWT stateless, el logout se maneja en el cliente eliminando el token.
        return ResponseEntity.ok("Logout exitoso");
    }
}
 
