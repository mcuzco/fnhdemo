package com.fnh.agenda.service;

import com.fnh.agenda.dto.CitaDTO;
import com.fnh.agenda.model.*;
import com.fnh.agenda.repository.CitaRepository;
import com.fnh.agenda.repository.EmpleadaRepository;
import com.fnh.agenda.repository.BeneficiariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
public class CitaService {
    @Autowired
    private CitaRepository citaRepo;
    @Autowired
    private BeneficiariaRepository benRepo;
    @Autowired
    private EmpleadaRepository empRepo;

    /** Listar citas, opcionalmente filtradas por sucursal */
    public Page<Cita> findAll(Pageable pageable, Sucursal sucursal) {
        if (sucursal != null) {
            return citaRepo.findBySucursal(sucursal, pageable);
        }
        return citaRepo.findAll(pageable);
    }

    /** Crear nueva cita con validación de disponibilidad */
    public Cita create(CitaDTO dto) {
        // Validar existencia de beneficiaria y empleada
        Beneficiaria b = benRepo.findById(dto.getIdBeneficiaria())
            .orElseThrow(() -> new IllegalArgumentException("Beneficiaria no encontrada"));
        Empleada e = empRepo.findById(dto.getIdProfesional())
            .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado"));

        // Validar slot libre
        LocalDate fecha = dto.getFecha();
        LocalTime hora = dto.getHora();
        Sucursal suc = dto.getSucursal();
        boolean ocupado = citaRepo.existsByProfesionalIdEmpleadaAndFechaAndHoraAndSucursal(
            e.getIdEmpleada(), fecha, hora, suc
        );
        if (ocupado) {
            throw new IllegalArgumentException("El profesional ya tiene una cita en ese horario");
        }

        // Crear Cita
        Cita c = new Cita();
        c.setFecha(fecha);
        c.setHora(hora);
        c.setBeneficiaria(b);
        c.setNombreBeneficiaria(b.getNombres() + " " + b.getApellidos());
        c.setProfesional(e);
        c.setNombreProfesional(e.getNombres() + " " + e.getApellidos());
        c.setTipoCaso(dto.getTipoCaso());
        c.setRiesgo(dto.getRiesgo());
        c.setEstado(dto.getEstado());
        c.setRelacionAgresor(dto.getRelacionAgresor());
        c.setDepartamento(dto.getDepartamento());
        c.setSucursal(suc);
        c.setDetalles(dto.getDetalles());
        return citaRepo.save(c);
    }

    /** Actualizar cita existente */
    public Cita update(Long id, CitaDTO dto) {
        Cita c = citaRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID " + id));

        // Validación disponibilidad si cambian fecha/hora/profesional/sucursal
        if (!c.getFecha().equals(dto.getFecha()) ||
            !c.getHora().equals(dto.getHora()) ||
            !c.getProfesional().getIdEmpleada().equals(dto.getIdProfesional()) ||
            !c.getSucursal().equals(dto.getSucursal())) {
            boolean ocupado = citaRepo.existsByProfesionalIdEmpleadaAndFechaAndHoraAndSucursal(
                dto.getIdProfesional(), dto.getFecha(), dto.getHora(), dto.getSucursal()
            );
            if (ocupado) {
                throw new IllegalArgumentException("Slot no disponible para el profesional");
            }
        }

        // Asignar nuevos valores
        Beneficiaria b = benRepo.findById(dto.getIdBeneficiaria()).get();
        Empleada e = empRepo.findById(dto.getIdProfesional()).get();
        c.setFecha(dto.getFecha());
        c.setHora(dto.getHora());
        c.setBeneficiaria(b);
        c.setNombreBeneficiaria(b.getNombres() + " " + b.getApellidos());
        c.setProfesional(e);
        c.setNombreProfesional(e.getNombres() + " " + e.getApellidos());
        c.setTipoCaso(dto.getTipoCaso());
        c.setRiesgo(dto.getRiesgo());
        c.setEstado(dto.getEstado());
        c.setRelacionAgresor(dto.getRelacionAgresor());
        c.setDepartamento(dto.getDepartamento());
        c.setSucursal(dto.getSucursal());
        c.setDetalles(dto.getDetalles());
        return citaRepo.save(c);
    }

    /** Eliminar cita */
    public void delete(Long id) {
        if (!citaRepo.existsById(id)) {
            throw new IllegalArgumentException("Cita no encontrada con ID " + id);
        }
        citaRepo.deleteById(id);
    }

    /** Buscar por ID */
    public Optional<Cita> findById(Long id) {
        return citaRepo.findById(id);
    }
}
