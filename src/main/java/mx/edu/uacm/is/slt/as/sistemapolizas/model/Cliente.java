package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column(name = "curp", length = 18, nullable = false)
    private String curp;

    @Column(name = "nombres", length = 64, nullable = false)
    private String nombres;

    @Column(name = "primer_apellido", length = 64, nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 64)
    private String segundoApellido;

    @Column(name = "direccion", length = 128)
    private String direccion;

    @Column(name = "fecha_nacimiento", columnDefinition = "DATE", nullable = false)
    private LocalDate fechaNacimiento;

    // Un cliente posee muchas pólizas, hay una relación de 1 a N
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Poliza> polizas = new ArrayList<>();

    // Constructor vacío, al parecer es requerido por JPA
    public Cliente() {}

    // Constructor con todos los parámetros
    public Cliente(String curp, String nombres, String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento) {
        this.curp = curp;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public List<Poliza> getPolizas() { return polizas; }
    public void setPolizas(List<Poliza> polizas) { this.polizas = polizas; }
}
