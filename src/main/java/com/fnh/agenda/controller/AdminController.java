package com.fnh.agenda.controller;

import com.fnh.agenda.dto.*;
import com.fnh.agenda.model.*;
import com.fnh.agenda.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired private BeneficiariaService benService;
    @Autowired private EmpleadaService empService;
    @Autowired private UsuarioService usrService;
    @Autowired private CitaService citaService;
    @Autowired private ReportService reportService;

    // --- Dashboard ---
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("porSucursal", reportService.countBySucursal());
        dashboardData.put("porDepartamento", reportService.countByDepartamento());
        return ResponseEntity.ok(dashboardData);
    }

    // --- Beneficiarias CRUD ---
    @GetMapping("/beneficiarias")
    public Page<Beneficiaria> listarBen(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pg = PageRequest.of(page, size, Sort.by("fechaRegistro").descending());
        return benService.findAll(pg, null, null);
    }

    @PostMapping("/beneficiarias")
    public ResponseEntity<Beneficiaria> crearBen(
        @Valid @RequestBody BeneficiariaDTO dto
    ) {
        Beneficiaria b = benService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(b);
    }

    @PutMapping("/beneficiarias/{id}")
    public Beneficiaria actualizarBen(
        @PathVariable Long id,
        @Valid @RequestBody BeneficiariaDTO dto
    ) {
        return benService.update(id, dto);
    }

    @DeleteMapping("/beneficiarias/{id}")
    public ResponseEntity<?> eliminarBen(@PathVariable Long id) {
        benService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Empleadas CRUD ---
    @GetMapping("/empleadas")
    public List<Empleada> listarEmp(@RequestParam(required = false) Sucursal sucursal) {
        return empService.findAll(sucursal);
    }

    @PostMapping("/empleadas")
    public ResponseEntity<Empleada> crearEmp(
        @Valid @RequestBody EmpleadaDTO dto
    ) {
        Empleada e = empService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    @PutMapping("/empleadas/{id}")
    public Empleada actualizarEmp(
        @PathVariable Long id,
        @Valid @RequestBody EmpleadaDTO dto
    ) {
        return empService.update(id, dto);
    }

    @DeleteMapping("/empleadas/{id}")
    public ResponseEntity<?> eliminarEmp(@PathVariable Long id) {
        empService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Usuarios CRUD ---
    @GetMapping("/usuarios")
    public List<Usuario> listarUsr() {
        return usrService.findAllUsers();
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsr(@PathVariable Long id) {
        usrService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // --- Citas CRUD ---
    @GetMapping("/citas")
    public Page<Cita> listarCitas(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sucursal sucursal
    ) {
        Pageable pg = PageRequest.of(page, size, Sort.by("fecha").descending());
        return citaService.findAll(pg, sucursal);
    }

    @PostMapping("/citas")
    public ResponseEntity<Cita> crearCita(@Valid @RequestBody CitaDTO dto) {
        Cita c = citaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @PutMapping("/citas/{id}")
    public Cita actualizarCita(
        @PathVariable Long id,
        @Valid @RequestBody CitaDTO dto
    ) {
        return citaService.update(id, dto);
    }

    @DeleteMapping("/citas/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
