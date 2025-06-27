package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class BeneficiarioPoliza {

    @EmbeddedId
    private BeneficiarioPolizaId id;

    private Integer porcentaje;

    public BeneficiarioPoliza() {
    }

    public BeneficiarioPoliza(BeneficiarioPolizaId id, Integer porcentaje) {
        this.id = id;
        this.porcentaje = porcentaje;
    }

    public BeneficiarioPoliza(java.util.UUID clavePoliza, String nombres,
                              String primerApellido, String segundoApellido,
                              java.time.LocalDate fechaNacimiento,
                              Integer porcentaje) {
        this.id = new BeneficiarioPolizaId(
                clavePoliza, nombres, primerApellido, segundoApellido, fechaNacimiento
        );
        this.porcentaje = porcentaje;
    }

    // getters y setters

    public BeneficiarioPolizaId getId() {
        return id;
    }
    public void setId(BeneficiarioPolizaId id) {
        this.id = id;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    // equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeneficiarioPoliza)) return false;
        BeneficiarioPoliza that = (BeneficiarioPoliza) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(porcentaje, that.porcentaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, porcentaje);
    }

    @Override
    public String toString() {
        return "BeneficiarioPoliza{" + "id=" + id + ", porcentaje=" + porcentaje + '}';
    }
}
