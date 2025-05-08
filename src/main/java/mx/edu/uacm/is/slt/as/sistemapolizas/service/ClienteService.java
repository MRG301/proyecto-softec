package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Listar todos los clientes
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    // Obtener un cliente por CURP
    public Optional<Cliente> buscarPorCurp(String curp) {
        return clienteRepository.findById(curp);
    }

    // Crear o guardar un cliente
    public Cliente guardar(Cliente nuevo) {
        return clienteRepository.save(nuevo);
    }

    // Actualizar un cliente existente
    public Optional<Cliente> actualizar(String curp, Cliente datosActualizados) {
        return clienteRepository.findById(curp)
                .map(existing -> {
                    existing.setNombres(datosActualizados.getNombres());
                    existing.setPrimerApellido(datosActualizados.getPrimerApellido());
                    existing.setSegundoApellido(datosActualizados.getSegundoApellido());
                    existing.setDireccion(datosActualizados.getDireccion());
                    existing.setFechaNacimiento(datosActualizados.getFechaNacimiento());

                    return clienteRepository.save(existing);
                });
    }

    // Eliminar un cliente por CURP
    public boolean eliminarPorCurp(String curp) {
        if (clienteRepository.existsById(curp)) {
            clienteRepository.deleteById(curp);
            return true;
        }
        return false;
    }
}