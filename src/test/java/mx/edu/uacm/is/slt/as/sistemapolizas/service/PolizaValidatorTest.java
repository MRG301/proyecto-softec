package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.exception.InconsistenciaDatosException;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PolizaValidatorTest {

    private final PolizaValidator validator = new PolizaValidator();

    private Poliza crearPolizaValida() {
        return new Poliza(UUID.randomUUID(), 1, 100.0, "desc", "CURP1234");
    }

    private Cliente crearClienteValido() {
        return new Cliente("CURP1234", "Nom", "PA", "SA", "dir", LocalDate.now());
    }

    @Test
    void montoNoPositivoDebeLanzarExcepcion() {
        Poliza p = crearPolizaValida();
        p.setMonto(0);
        assertThrows(InconsistenciaDatosException.class, () -> validator.validarParaCrear(p, crearClienteValido()));
    }

    @Test
    void tipoNullDebeLanzarExcepcion() {
        Poliza p = crearPolizaValida();
        p.setTipo(null);
        assertThrows(InconsistenciaDatosException.class, () -> validator.validarParaCrear(p, crearClienteValido()));
    }

    @Test
    void tipoNegativoDebeLanzarExcepcion() {
        Poliza p = crearPolizaValida();
        p.setTipo(-1);
        assertThrows(InconsistenciaDatosException.class, () -> validator.validarParaCrear(p, crearClienteValido()));
    }

    @Test
    void tipoMayorADosDebeLanzarExcepcion() {
        Poliza p = crearPolizaValida();
        p.setTipo(3);
        assertThrows(InconsistenciaDatosException.class, () -> validator.validarParaCrear(p, crearClienteValido()));
    }

    @Test
    void clienteNuloDebeLanzarExcepcion() {
        Poliza p = crearPolizaValida();
        assertThrows(InconsistenciaDatosException.class, () -> validator.validarParaCrear(p, null));
    }

    @Test
    void datosValidosNoLanzanExcepcion() {
        Poliza p = crearPolizaValida();
        Cliente c = crearClienteValido();
        assertDoesNotThrow(() -> validator.validarParaCrear(p, c));
    }
}
