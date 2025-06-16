package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Poliza {

    @Id
    private UUID clave;

    private Integer tipo;
    private double monto;
    private String descripcion;
    private String curpCliente;

    public Poliza() {
    }

    public Poliza(UUID clave, Integer tipo,
                  double monto, String descripcion,
                  String curpCliente) {
        this.clave = clave;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.curpCliente = curpCliente;
    }

    // getters y setters

    public UUID getClave() {
        return clave;
    }
    public void setClave(UUID clave) {
        this.clave = clave;
    }

    public Integer getTipo() {
        return tipo;
    }
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCurpCliente() {
        return curpCliente;
    }
    public void setCurpCliente(String curpCliente) {
        this.curpCliente = curpCliente;
    }

    // equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Poliza)) return false;
        Poliza that = (Poliza) o;
        return Double.compare(that.monto, monto) == 0 &&
                Objects.equals(clave, that.clave) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(curpCliente, that.curpCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave, tipo, monto, descripcion, curpCliente);
    }

    @Override
    public String toString() {
        return "Poliza{" + "clave=" + clave +
                ", tipo='" + tipo + '\'' + ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", curpCliente='" + curpCliente + '\'' + '}';
    }
}