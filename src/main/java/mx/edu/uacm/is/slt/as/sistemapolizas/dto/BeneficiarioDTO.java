package mx.edu.uacm.is.slt.as.sistemapolizas.dto;

import java.time.OffsetDateTime;

// Beneficiario remoto
public record BeneficiarioDTO(
        String nombres,
        String primerApellido,
        String segundoApellido,
        OffsetDateTime fechaNacimiento,
        Integer porcentaje
) {
}
