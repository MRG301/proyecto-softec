package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "polizas")
public class Poliza {

    @Id
    @Column(name = "clave", columnDefinition = "UUID", nullable = false)
    private UUID clave;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    @Column(name = "monto", precision = 9, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    // Muchas pólizas pertenecen a un cliente, es decir una relación de N a 1
    @ManyToOne(optional = false)
    @JoinColumn(name = "curp_cliente", nullable = false)
    private Cliente cliente;

    // Una póliza posee muchos beneficiarios, es decir, hay una relación 1 a N
    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeneficiarioPoliza> beneficiarios = new ArrayList<>();

    // Constructor vacío
    public Poliza() {

    }

    // Constructor con parámetros, sin la lista de beneficiarios que se gestionan aparte.
    public Poliza(UUID clave, Integer tipo, BigDecimal monto, String descripcion, Cliente cliente) {
        this.clave = clave;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cliente = cliente;
    }

    // Getters y Setters
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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<BeneficiarioPoliza> getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(List<BeneficiarioPoliza> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }
}