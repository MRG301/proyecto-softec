package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class BeneficiarioPolizaId implements Serializable {

    private UUID clavePoliza;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;

    public BeneficiarioPolizaId() {
    }

    public BeneficiarioPolizaId(UUID clavePoliza, String nombres,
                                String primerApellido, String segundoApellido,
                                LocalDate fechaNacimiento) {
        this.clavePoliza = clavePoliza;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    // getters y setters

    public UUID getClavePoliza() {
        return clavePoliza;
    }
    public void setClavePoliza(UUID clavePoliza) {
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

    // equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeneficiarioPolizaId)) return false;
        BeneficiarioPolizaId that = (BeneficiarioPolizaId) o;
        return Objects.equals(clavePoliza, that.clavePoliza) &&
                Objects.equals(nombres, that.nombres) &&
                Objects.equals(primerApellido, that.primerApellido) &&
                Objects.equals(segundoApellido, that.segundoApellido) &&
                Objects.equals(fechaNacimiento, that.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clavePoliza, nombres, primerApellido, segundoApellido, fechaNacimiento);
    }

    @Override
    public String toString() {
        return "BeneficiarioPolizaId{" +
                "clavePoliza=" + clavePoliza +
                ", nombres='" + nombres + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}