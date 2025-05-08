package mx.edu.uacm.is.slt.as.sistemapolizas.model;

import jakarta.persistence.*;

// Entidad BeneficiarioPoliza que utiliza la clave compuesta
@Entity
@Table(name = "beneficiarios_poliza")
public class BeneficiarioPoliza {

    @EmbeddedId
    private BeneficiarioPolizaId id;

    @MapsId("clavePoliza")
    @ManyToOne(optional = false)
    @JoinColumn(name = "clave_poliza", nullable = false)
    private Poliza poliza;

    @Column(name = "porcentaje", nullable = false)
    private Integer porcentaje;

    public BeneficiarioPoliza() { }

    public BeneficiarioPoliza(Poliza poliza,
                              String nombres,
                              String primerApellido,
                              String segundoApellido,
                              java.time.LocalDate fechaNacimiento,
                              Integer porcentaje) {
        this.id        = new BeneficiarioPolizaId(
                poliza.getClave(), nombres, primerApellido, segundoApellido, fechaNacimiento
        );
        this.poliza    = poliza;
        this.porcentaje = porcentaje;
    }

    public BeneficiarioPolizaId getId() {
        return id;
    }

    public void setId(BeneficiarioPolizaId id) {
        this.id = id;
    }

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
        if (this.id == null) {
            this.id = new BeneficiarioPolizaId();
        }
        this.id.setClavePoliza(poliza != null ? poliza.getClave() : null);
    }

    // getters y setters sobre los campos del id embebido

    public String getNombres() {
        return id.getNombres();
    }
    public void setNombres(String nombres) {
        this.id.setNombres(nombres);
    }

    public String getPrimerApellido() {
        return id.getPrimerApellido();
    }
    public void setPrimerApellido(String primerApellido) {
        this.id.setPrimerApellido(primerApellido);
    }

    public String getSegundoApellido() {
        return id.getSegundoApellido();
    }
    public void setSegundoApellido(String segundoApellido) {
        this.id.setSegundoApellido(segundoApellido);
    }

    public java.time.LocalDate getFechaNacimiento() {
        return id.getFechaNacimiento();
    }
    public void setFechaNacimiento(java.time.LocalDate fechaNacimiento) {
        this.id.setFechaNacimiento(fechaNacimiento);
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }
}