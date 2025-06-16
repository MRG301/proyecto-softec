package mx.edu.uacm.is.slt.as.sistemapolizas.mapper;

import mx.edu.uacm.is.slt.as.sistemapolizas.dto.ClienteDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;

public interface ClienteMapper {
    static Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
                dto.curp(),
                dto.nombres(),
                dto.primerApellido(),
                dto.segundoApellido(),
                dto.direccion(),
                dto.fechaNacimiento().toLocalDate()
        );
    }
}
