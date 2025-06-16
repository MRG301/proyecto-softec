package mx.edu.uacm.is.slt.as.sistemapolizas.dto;

import java.time.OffsetDateTime;

// Cliente remoto
public record ClienteDTO(
        String curp,
        String nombres,
        String primerApellido,
        String segundoApellido,
        String direccion,
        OffsetDateTime fechaNacimiento
){
}