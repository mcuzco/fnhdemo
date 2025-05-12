package com.fnh.agenda.dto;

import com.fnh.agenda.model.*;
import com.fnh.agenda.model.Riesgo; // Ensure NivelRiesgo is imported
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDTO {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotNull(message = "El ID de la beneficiaria es obligatorio")
    private Long idBeneficiaria;

    @NotNull(message = "El ID del profesional es obligatorio")
    private Long idProfesional;

    @NotNull(message = "El tipo de caso es obligatorio")
    private TipoCaso tipoCaso;

    @NotNull(message = "El nivel de riesgo es obligatorio")
    private Riesgo riesgo;

    @NotNull(message = "El estado de la cita es obligatorio")
    private EstadoCita estado;

    @NotNull(message = "La relación con el agresor es obligatoria")
    private RelacionAgresor relacionAgresor;

    @NotNull(message = "El departamento es obligatorio")
    private Departamento departamento;

    @NotNull(message = "La sucursal es obligatoria")
    private Sucursal sucursal;

    @Size(max = 500, message = "Los detalles no deben superar los 500 caracteres")
    private String detalles;

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Long getIdBeneficiaria() {
        return idBeneficiaria;
    }

    public void setIdBeneficiaria(Long idBeneficiaria) {
        this.idBeneficiaria = idBeneficiaria;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public Riesgo getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(Riesgo riesgo) {
        this.riesgo = riesgo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public RelacionAgresor getRelacionAgresor() {
        return relacionAgresor;
    }

    public void setRelacionAgresor(RelacionAgresor relacionAgresor) {
        this.relacionAgresor = relacionAgresor;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
