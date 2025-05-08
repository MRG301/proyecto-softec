package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class BeneficiarioPolizaId implements Serializable {

    @Column(name = "clave_poliza", columnDefinition = "UUID", nullable = false)
    private java.util.UUID clavePoliza;

    @Column(name = "nombres", length = 64, nullable = false)
    private String nombres;

    @Column(name = "primer_apellido", length = 64, nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 64)
    private String segundoApellido;

    @Column(name = "fecha_nacimiento", columnDefinition = "DATE", nullable = false)
    private LocalDate fechaNacimiento;

    public BeneficiarioPolizaId() {}

    public BeneficiarioPolizaId(java.util.UUID clavePoliza,
                                String nombres,
                                String primerApellido,
                                String segundoApellido,
                                LocalDate fechaNacimiento) {
        this.clavePoliza     = clavePoliza;
        this.nombres         = nombres;
        this.primerApellido  = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public java.util.UUID getClavePoliza() {
        return clavePoliza;
    }

    public void setClavePoliza(java.util.UUID clavePoliza) {
        this.clavePoliza = clavePoliza;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeneficiarioPolizaId)) return false;
        BeneficiarioPolizaId that = (BeneficiarioPolizaId) o;
        return Objects.equals(clavePoliza, that.clavePoliza)
                && Objects.equals(nombres, that.nombres)
                && Objects.equals(primerApellido, that.primerApellido)
                && Objects.equals(segundoApellido, that.segundoApellido)
                && Objects.equals(fechaNacimiento, that.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clavePoliza, nombres, primerApellido, segundoApellido, fechaNacimiento);
    }
}