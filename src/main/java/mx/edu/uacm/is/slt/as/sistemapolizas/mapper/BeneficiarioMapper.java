package mx.edu.uacm.is.slt.as.sistemapolizas.mapper;

import mx.edu.uacm.is.slt.as.sistemapolizas.dto.BeneficiarioDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPolizaId;

import java.util.UUID;

public interface BeneficiarioMapper {
    static BeneficiarioPoliza toEntity(BeneficiarioDTO dto, UUID clavePoliza) {
        BeneficiarioPolizaId id = new BeneficiarioPolizaId(
                clavePoliza,
                dto.nombres(),
                dto.primerApellido(),
                dto.segundoApellido(),
                dto.fechaNacimiento().toLocalDate()
        );
        return new BeneficiarioPoliza(id, dto.porcentaje());
    }

    static BeneficiarioDTO toDto(BeneficiarioPoliza entity) {
        BeneficiarioPolizaId id = entity.getId();
        return new BeneficiarioDTO(
                id.getNombres(),
                id.getPrimerApellido(),
                id.getSegundoApellido(),
                id.getFechaNacimiento().atStartOfDay().atOffset(java.time.ZoneOffset.UTC),
                entity.getPorcentaje()
        );
    }
}
