package mx.edu.uacm.is.slt.as.sistemapolizas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

// Poliza en remoto
@JsonIgnoreProperties(ignoreUnknown = true)
public record PolizaDTO(
        UUID   clave,
        int    tipo,
        double monto,
        String descripcion,
        String curpCliente     // lo único que envía la API
) {
}
