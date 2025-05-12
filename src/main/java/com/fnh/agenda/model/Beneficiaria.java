package com.fnh.agenda.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "beneficiaria")
public class Beneficiaria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiaria")
    private Long idBeneficiaria;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "codigo_carpeta", nullable = false, unique = true, length = 20)
    private String codigoCarpeta;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "edad")
    private String edad;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @OneToMany(mappedBy = "beneficiaria")
    private List<Cita> citas;

    // Constructor vacío requerido por JPA
    public Beneficiaria() {}

    // Getters y Setters
    public Long getIdBeneficiaria() {
        return idBeneficiaria;
    }

    public void setIdBeneficiaria(Long idBeneficiaria) {
        this.idBeneficiaria = idBeneficiaria;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoCarpeta() {
        return codigoCarpeta;
    }

    public void setCodigoCarpeta(String codigoCarpeta) {
        this.codigoCarpeta = codigoCarpeta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
}
