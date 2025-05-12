package com.fnh.agenda.controller;

import com.fnh.agenda.dto.CitaDTO;
import com.fnh.agenda.model.Cita;
import com.fnh.agenda.model.Sucursal;
import com.fnh.agenda.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citas")
@PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
public class CitaController {

    @Autowired private CitaService citaService;

    @GetMapping
    public Page<Cita> listarCitas(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sucursal sucursal
    ) {
        Pageable pg = PageRequest.of(page, size, Sort.by("fecha").descending());
        return citaService.findAll(pg, sucursal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> verCita(@PathVariable Long id) {
        return citaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cita> crearCita(@Valid @RequestBody CitaDTO dto) {
        Cita c = citaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(
        @PathVariable Long id,
        @Valid @RequestBody CitaDTO dto
    ) {
        Cita actualizada = citaService.update(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
 
