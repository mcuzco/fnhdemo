package com.fnh.agenda.service;

import com.fnh.agenda.model.Cita;
import com.fnh.agenda.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService {
    @Autowired
    private CitaRepository citaRepo;

    /** Conteo de citas por sucursal */
    public Map<String, Long> countBySucursal() {
        List<Cita> todas = citaRepo.findAll();
        return todas.stream()
            .collect(Collectors.groupingBy(c -> c.getSucursal().name(), Collectors.counting()));
    }

    /** Conteo de citas por departamento */
    public Map<String, Long> countByDepartamento() {
        List<Cita> todas = citaRepo.findAll();
        return todas.stream()
            .collect(Collectors.groupingBy(c -> c.getDepartamento().name(), Collectors.counting()));
    }

    /** Conteo de citas por estado */
    public Map<String, Long> countByEstado() {
        List<Cita> todas = citaRepo.findAll();
        return todas.stream()
            .collect(Collectors.groupingBy(c -> c.getEstado().name(), Collectors.counting()));
    }
}
 
