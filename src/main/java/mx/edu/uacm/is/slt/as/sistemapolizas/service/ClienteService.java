package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final SincronizacionService syncService;

    // listar todos los clientes
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    // obtener un cliente por CURP
    public Optional<Cliente> buscarPorCurp(String curp) {
        return clienteRepository.findById(curp);
    }

    // crear o guardar un cliente
    public Cliente guardar(Cliente nuevo) {
        return syncService.crearClienteLocal(nuevo);
    }

    // actualizar un cliente existente
    public Optional<Cliente> actualizar(String curp, Cliente datosActualizados) {
        return clienteRepository.findById(curp)
                .map(existing -> {
                    existing.setNombres(datosActualizados.getNombres());
                    existing.setPrimerApellido(datosActualizados.getPrimerApellido());
                    existing.setSegundoApellido(datosActualizados.getSegundoApellido());
                    existing.setDireccion(datosActualizados.getDireccion());
                    existing.setFechaNacimiento(datosActualizados.getFechaNacimiento());

                    return syncService.actualizarClienteLocal(existing);
                });
    }

    // eliminar un cliente por CURP
    public boolean eliminarPorCurp(String curp) {
        if (clienteRepository.existsById(curp)) {
            syncService.eliminarClienteLocal(curp);
            return true;
        }
        return false;
    }
}
