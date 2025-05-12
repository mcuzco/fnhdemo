package com.fnh.agenda.service;

import com.fnh.agenda.dto.EmpleadaDTO;
import com.fnh.agenda.model.Departamento;
import com.fnh.agenda.model.Empleada;
import com.fnh.agenda.model.Sucursal;
import com.fnh.agenda.repository.EmpleadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmpleadaService {
    @Autowired
    private EmpleadaRepository repo;

    /** Listar todas o filtrar por sucursal */
    public List<Empleada> findAll(Sucursal sucursal) {
        if (sucursal != null) {
            return repo.findBySucursal(sucursal);
        }
        return repo.findAll();
    }

    /** Crear nueva empleada */
    public Empleada create(EmpleadaDTO dto) {
        if (repo.findByCedula(dto.getCedula()).isPresent()) {
            throw new IllegalArgumentException("Cédula ya existe");
        }
        Empleada e = new Empleada();
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setCedula(dto.getCedula());
        e.setTelefono(dto.getTelefono());
        e.setCorreo(dto.getCorreo());

        // Map String to Departamento enum
        try {
            Departamento departamento = Departamento.valueOf(dto.getDepartamento().name().toUpperCase());
            e.setDepartamento(departamento);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Departamento inválido: " + dto.getDepartamento());
        }

        e.setSucursal(dto.getSucursal());
        return repo.save(e);
    }

    /** Actualizar empleada existente */
    public Empleada update(Long id, EmpleadaDTO dto) {
        Empleada e = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Empleada no encontrada con ID " + id));
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        // cedula no editable tras creación
        e.setTelefono(dto.getTelefono());
        e.setCorreo(dto.getCorreo());

        // Map String to Departamento enum
        try {
            Departamento departamento = dto.getDepartamento();
            e.setDepartamento(departamento);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Departamento inválido: " + dto.getDepartamento());
        }

        e.setSucursal(dto.getSucursal());
        return repo.save(e);
    }

    /** Eliminar empleada */
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Empleada no encontrada con ID " + id);
        }
        repo.deleteById(id);
    }
}
