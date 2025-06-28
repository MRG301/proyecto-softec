package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.ClienteDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.PolizaDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.extern.PolizaExternalClient;
import mx.edu.uacm.is.slt.as.sistemapolizas.mapper.ClienteMapper;
import mx.edu.uacm.is.slt.as.sistemapolizas.mapper.PolizaMapper;
import mx.edu.uacm.is.slt.as.sistemapolizas.mapper.BeneficiarioMapper;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.BeneficiarioPolizaRepository;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.ClienteRepository;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.PolizaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SincronizacionService {

    private final PolizaExternalClient external;
    private final ClienteRepository clienteRepo;
    private final PolizaRepository polizaRepo;
    private final BeneficiarioPolizaRepository beneficiarioRepo;

    /**
     * se ejecuta al inicio de la aplicación, obtiene todo el
     * catálogo del sistema remoto y lo copia/actualiza en la BD local
     * 1) Trae la lista completa de PolizaDTO desde el sistema remoto
     * 2) Por cada registro remoto
     *    si el cliente remoto no existe local → lo inserta o actualiza campos si ya está
     *    inserta/actualiza la póliza mapeo DTO→Entity
     *    borra todos los beneficiarios locales de esa póliza
     *    Revisar vuelve a traer TODOS los beneficiarios remotos para esa póliza
     *    y los inserta uno a uno o actualiza si cambia porcentaje.
     */
    @Transactional
    public void sincronizarTodo() {            // en StartupSync se llama
        List<PolizaDTO> remotas = external.obtenerTodasLasPolizas();
        if (remotas == null) {
            return;
        }

        for (PolizaDTO pDto : remotas) {
            // clientes
            ClienteDTO cDto = external.obtenerCliente(pDto.curpCliente());
            clienteRepo.save(ClienteMapper.toEntity(cDto));
            // pólizas
            Poliza polizaLocal = PolizaMapper.toEntity(pDto);
            polizaRepo.save(polizaLocal);

            // Beneficiarios: se reemplazan con los obtenidos del servicio remoto
            var beneficiariosRemotos = external.obtenerBeneficiariosPorPoliza(pDto.clave());
            // elimina todos los beneficiarios locales existentes y coloca los nuevos
            beneficiarioRepo.deleteAllByIdClavePoliza(pDto.clave());

            if (beneficiariosRemotos != null) {
                beneficiariosRemotos.forEach(bDto ->
                        beneficiarioRepo.save(BeneficiarioMapper.toEntity(bDto, pDto.clave())));
            }
        }
    }


    /** cuando en la BD local se crea o actualiza una póliza este
     * método debe llamarse para propagar el cambio al sistema remoto */
    public Poliza crearOActualizarPolizaLocal(Poliza entidadLocal) {
        // guardo local
        Poliza guardada = polizaRepo.save(entidadLocal);

        // actualiza/crea en remoto
        PolizaDTO dto = PolizaMapper.toDto(guardada);
        // si falla, puedes manejar excepción
        external.actualizarPolizaRemota(dto);
        return guardada;
    }

    // lo mismo para eliminar
    public void eliminarPolizaLocal(UUID clave) {
        // borra en local
        polizaRepo.deleteById(clave);

        // borra en remoto
        external.eliminarPolizaRemota(clave);
    }

    // hacer de crear/actualizar/eliminar cliente local y remoto
    // también hacer de beneficiarios local y remoto

}
