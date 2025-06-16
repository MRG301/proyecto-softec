package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Cliente {

    @Id
    private String curp;

    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String direccion;
    private LocalDate fechaNacimiento;

    public Cliente() {
    }

    public Cliente(String curp, String nombres,
                   String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento) {
        this.curp = curp;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    // getters y setters

    public String getCurp() {
        return curp;
    }
    public void setCurp(String curp) {
        this.curp = curp;
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

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
        if (!(o instanceof Cliente)) return false;
        Cliente that = (Cliente) o;
        return Objects.equals(curp, that.curp) &&
                Objects.equals(nombres, that.nombres) &&
                Objects.equals(primerApellido, that.primerApellido) &&
                Objects.equals(segundoApellido, that.segundoApellido) &&
                Objects.equals(direccion, that.direccion) &&
                Objects.equals(fechaNacimiento, that.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curp, nombres, primerApellido,
                segundoApellido, direccion, fechaNacimiento);
    }

    @Override
    public String toString() {
        return "Cliente{" + "curp='" + curp + '\'' +
                ", nombres='" + nombres + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}