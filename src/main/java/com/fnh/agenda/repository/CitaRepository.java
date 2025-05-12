package com.fnh.agenda.repository;

import com.fnh.agenda.model.Cita;
import com.fnh.agenda.model.Sucursal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Page<Cita> findBySucursal(Sucursal sucursal, Pageable pageable);
    boolean existsByProfesionalIdEmpleadaAndFechaAndHoraAndSucursal(
        Long idProfesional, LocalDate fecha, LocalTime hora, Sucursal sucursal);
}
 
