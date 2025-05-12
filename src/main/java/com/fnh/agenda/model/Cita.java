package com.fnh.agenda.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_profesional", "fecha", "hora", "sucursal"})
})
public class Cita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "id_beneficiaria", nullable = false)
    private Beneficiaria beneficiaria;

    @Column(name = "nombre_beneficiaria", nullable = false, length = 100)
    private String nombreBeneficiaria;

    @Column(name = "edad", nullable = false) // Cambiado a "edad"
    private Integer edad; // Cambiado a "edad"

    @ManyToOne
    @JoinColumn(name = "id_profesional", nullable = false)
    private Empleada profesional;

    @Column(name = "nombre_profesional", nullable = false, length = 100)
    private String nombreProfesional;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_caso", nullable = false, length = 30)
    private TipoCaso tipoCaso;

    @Enumerated(EnumType.STRING)
    @Column(name = "riesgo", nullable = false, length = 10)
    private Riesgo riesgo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoCita estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "relacion_agresor", nullable = false, length = 20)
    private RelacionAgresor relacionAgresor;

    @Enumerated(EnumType.STRING)
    @Column(name = "departamento", nullable = false, length = 20)
    private Departamento departamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sucursal", nullable = false, length = 20)
    private Sucursal sucursal;

    @Column(name = "detalles", columnDefinition = "TEXT")
    private String detalles;

    // Constructor vacío
    public Cita() {}

    // Getters y Setters
    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

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

    public Beneficiaria getBeneficiaria() {
        return beneficiaria;
    }

    public void setBeneficiaria(Beneficiaria beneficiaria) {
        this.beneficiaria = beneficiaria;
    }

    public String getNombreBeneficiaria() {
        return nombreBeneficiaria;
    }

    public void setNombreBeneficiaria(String nombreBeneficiaria) {
        this.nombreBeneficiaria = nombreBeneficiaria;
    }

    public Integer getEdad() { // Cambiado a "edad"
        return edad;
    }

    public void setEdad(Integer edad) { // Cambiado a "edad"
        this.edad = edad;
    }

    public Empleada getProfesional() {
        return profesional;
    }

    public void setProfesional(Empleada profesional) {
        this.profesional = profesional;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
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
