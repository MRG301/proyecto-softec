package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.exception.InconsistenciaDatosException;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import org.springframework.stereotype.Component;

@Component
public class PolizaValidator {

    public void validarParaCrear(Poliza p, Cliente cliente) {

        if (p.getMonto() <= 0)
            throw new InconsistenciaDatosException("El monto asegurado debe ser positivo");

        // validar tipo numérico en 0,1,2
        Integer tipo = p.getTipo();
        if (tipo == null || tipo < 0 || tipo > 2) {
            throw new InconsistenciaDatosException("tipo de póliza inválido 0-AUTO, 1-VIDA, 2-MÉDICO");
        }

        if (cliente == null)
            throw new InconsistenciaDatosException("No existe el cliente con CURP " + p.getCurpCliente());
    }
}
