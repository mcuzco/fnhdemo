package com.fnh.agenda.service;

import com.fnh.agenda.dto.BeneficiariaDTO;
import com.fnh.agenda.model.Beneficiaria;
import com.fnh.agenda.repository.BeneficiariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BeneficiariaService {
    @Autowired
    private BeneficiariaRepository repo;

    /** Listado con filtros y paginación */
    public Page<Beneficiaria> findAll(Pageable pageable, String filtroNombre, String filtroCarpeta) {
        // Para búsquedas simples, se podría implementar Specification o Query Methods
        if ((filtroNombre == null || filtroNombre.trim().isEmpty()) 
         && (filtroCarpeta == null || filtroCarpeta.trim().isEmpty())) {
            return repo.findAll(pageable);
        }
        // Aquí irían consultas personalizadas; por ahora devolvemos todo
        return repo.findAll(pageable);
    }

    /** Crear beneficiaria nueva */
    public Beneficiaria create(BeneficiariaDTO dto) {
        if (repo.existsByCodigoCarpeta(dto.getCodigoCarpeta())) {
            throw new IllegalArgumentException("Código de carpeta ya existe");
        }
        Beneficiaria b = new Beneficiaria();
        b.setNombres(dto.getNombres());
        b.setApellidos(dto.getApellidos());
        b.setTelefono(dto.getTelefono());
        b.setCodigoCarpeta(dto.getCodigoCarpeta());
        b.setDireccion(dto.getDireccion());
        b.setEdad(String.valueOf(dto.getEdad()));
        return repo.save(b);
    }

    /** Actualizar beneficiaria existente */
    public Beneficiaria update(Long id, BeneficiariaDTO dto) {
        Beneficiaria b = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Beneficiaria no encontrada con ID " + id));
        // Solo campos editables
        b.setNombres(dto.getNombres());
        b.setApellidos(dto.getApellidos());
        b.setTelefono(dto.getTelefono());
        // codigoCarpeta inmutable; no lo cambiamos
        b.setDireccion(dto.getDireccion());
        b.setEdad(String.valueOf(dto.getEdad()));
        return repo.save(b);
    }

    /** Eliminar beneficiaria */
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Beneficiaria no encontrada con ID " + id);
        }
        repo.deleteById(id);
    }

    /** Buscar por ID */
    public Optional<Beneficiaria> findById(Long id) {
        return repo.findById(id);
    }
}
 
