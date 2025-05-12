package com.fnh.agenda.repository;

import com.fnh.agenda.model.Empleada;
import com.fnh.agenda.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmpleadaRepository extends JpaRepository<Empleada, Long> {
    Optional<Empleada> findByCedula(String cedula);
    List<Empleada> findBySucursal(Sucursal sucursal);
}

