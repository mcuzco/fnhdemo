package com.fnh.agenda.repository;

import com.fnh.agenda.model.Beneficiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BeneficiariaRepository extends JpaRepository<Beneficiaria, Long> {
    Optional<Beneficiaria> findByCodigoCarpeta(String codigoCarpeta);
    Boolean existsByCodigoCarpeta(String codigoCarpeta);
}

