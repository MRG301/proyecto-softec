package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.ClienteDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.PolizaDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.BeneficiarioDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.extern.PolizaExternalClient;
import mx.edu.uacm.is.slt.as.sistemapolizas.mapper.ClienteMapper;
import mx.edu.uacm.is.slt.as.sistemapolizas.mapper.BeneficiarioMapper;
import mx.edu.uacm.is.slt.as.sistemapolizas.mapper.PolizaMapper;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
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
        List<PolizaDTO> remotas;
        try {
            remotas = external.obtenerTodasLasPolizas();
        } catch (Exception e) {
            // si la comunicación falla no hacemos nada
            return;
        }

        for (PolizaDTO pDto : remotas) {
            try {
                // clientes
                ClienteDTO cDto = external.obtenerCliente(pDto.curpCliente());
                if (cDto != null) {
                    clienteRepo.save(ClienteMapper.toEntity(cDto));
                }

                // pólizas
                Poliza polizaLocal = PolizaMapper.toEntity(pDto);
                polizaRepo.save(polizaLocal);

                // beneficiarios
                beneficiarioRepo.deleteAllByIdClavePoliza(pDto.clave());
                List<BeneficiarioDTO> beneficiarios = external.obtenerBeneficiarios(pDto.clave());
                for (BeneficiarioDTO bDto : beneficiarios) {
                    BeneficiarioPoliza b = BeneficiarioMapper.toEntity(bDto, pDto.clave());
                    beneficiarioRepo.save(b);
                }
            } catch (Exception ex) {
                // errores individuales se ignoran para continuar con el resto
            }
        }
    }


    /**
     * Crea una póliza local y también la registra en el sistema remoto.
     */
    public Poliza crearPolizaLocal(Poliza nueva) {
        Poliza guardada = polizaRepo.save(nueva);
        external.crearPolizaRemota(PolizaMapper.toDto(guardada));
        return guardada;
    }

    /**
     * Actualiza una póliza local y refleja el cambio en el sistema remoto.
     */
    public Poliza actualizarPolizaLocal(Poliza actualizada) {
        Poliza guardada = polizaRepo.save(actualizada);
        external.actualizarPolizaRemota(PolizaMapper.toDto(guardada));
        return guardada;
    }

    // compatibilidad con código existente
    public Poliza crearOActualizarPolizaLocal(Poliza p) {
        return actualizarPolizaLocal(p);
    }

    // eliminar póliza local y en remoto
    public void eliminarPolizaLocal(UUID clave) {
        polizaRepo.deleteById(clave);
        external.eliminarPolizaRemota(clave);
    }

    // ---- operaciones para clientes ----

    public Cliente crearClienteLocal(Cliente nuevo) {
        Cliente guardado = clienteRepo.save(nuevo);
        external.crearClienteRemoto(ClienteMapper.toDto(guardado));
        return guardado;
    }

    public Cliente actualizarClienteLocal(Cliente actualizado) {
        Cliente guardado = clienteRepo.save(actualizado);
        external.actualizarClienteRemoto(ClienteMapper.toDto(guardado));
        return guardado;
    }

    public void eliminarClienteLocal(String curp) {
        clienteRepo.deleteById(curp);
        // si hubiera end-point para borrar en remoto se llamaría aquí
    }

    // ---- operaciones para beneficiarios ----

    public BeneficiarioPoliza crearBeneficiarioLocal(BeneficiarioPoliza nuevo) {
        BeneficiarioPoliza guardado = beneficiarioRepo.save(nuevo);
        external.crearBeneficiarioRemoto(nuevo.getId().getClavePoliza(),
                BeneficiarioMapper.toDto(guardado));
        return guardado;
    }

}
