package mx.edu.uacm.is.slt.as.sistemapolizas.mapper;

import mx.edu.uacm.is.slt.as.sistemapolizas.dto.PolizaDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;

public interface PolizaMapper {
    public static Poliza toEntity(PolizaDTO dto) {
        return new Poliza(
                dto.clave(),
                dto.tipo(),
                dto.monto(),
                dto.descripcion(),
                dto.curpCliente()
        );
    }

    public static PolizaDTO toDto(Poliza entity) {
        return new PolizaDTO(
                entity.getClave(),
                entity.getTipo(),
                entity.getMonto(),
                entity.getDescripcion(),
                entity.getCurpCliente()
        );
    }
}
