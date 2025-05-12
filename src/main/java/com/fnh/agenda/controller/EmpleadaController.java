package com.fnh.agenda.controller;

import com.fnh.agenda.dto.CitaDTO;
import com.fnh.agenda.model.Cita;
import com.fnh.agenda.model.Sucursal;
import com.fnh.agenda.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/empleada")
@PreAuthorize("hasAnyRole('EMPLEADO','ADMIN')")
public class EmpleadaController {

    @Autowired private CitaService citaService;

    @GetMapping("/citas")
    public Page<Cita> misCitas(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Authentication auth
    ) {
        // Obtener sucursal del usuario autenticado
        String sucursalName = auth.getAuthorities().iterator().next().getAuthority().substring(5);
        Sucursal suc = Sucursal.valueOf(sucursalName);
        Pageable pg = PageRequest.of(page, size, Sort.by("fecha").descending());
        return citaService.findAll(pg, suc);
    }

    @PostMapping("/citas")
    public ResponseEntity<Cita> agendarCita(
        @Valid @RequestBody CitaDTO dto,
        Authentication auth
    ) {
        // Forzar que la cita sea de la sucursal del usuario
        String sucursalName = auth.getAuthorities().iterator().next().getAuthority().substring(5);
        dto.setSucursal(Sucursal.valueOf(sucursalName));
        Cita c = citaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @PutMapping("/citas/{id}")
    public Cita editarCita(
        @PathVariable Long id,
        @Valid @RequestBody CitaDTO dto,
        Authentication auth
    ) {
        String sucursalName = auth.getAuthorities().iterator().next().getAuthority().substring(5);
        dto.setSucursal(Sucursal.valueOf(sucursalName));
        return citaService.update(id, dto);
    }

    @DeleteMapping("/citas/{id}")
    public ResponseEntity<?> borrarCita(
        @PathVariable Long id,
        Authentication auth
    ) {
        // Opcional: validar que la cita pertenezca a la sucursal
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
 
