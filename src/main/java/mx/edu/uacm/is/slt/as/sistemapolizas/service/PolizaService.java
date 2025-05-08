package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.PolizaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PolizaService {
    private final PolizaRepository polizaRepository;

    @Autowired
    public PolizaService(PolizaRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    // Listar todas las pólizas
    public List<Poliza> buscarTodas() {
        return polizaRepository.findAll();
    }

    // Obtener una póliza por UUID
    public Optional<Poliza> buscarPorClave(UUID clave) {
        return polizaRepository.findById(clave);
    }

    // Crear o guardar una póliza
    public Poliza guardar(Poliza nueva) {
        return polizaRepository.save(nueva);
    }

    // Actualizar una póliza existente
    public Optional<Poliza> actualizar(UUID clave, Poliza datosActualizados) {
        return polizaRepository.findById(clave)
                .map(existing -> {
                    existing.setTipo(datosActualizados.getTipo());
                    existing.setMonto(datosActualizados.getMonto());
                    existing.setDescripcion(datosActualizados.getDescripcion());
                    existing.setCliente(datosActualizados.getCliente());
                    // ...
                    return polizaRepository.save(existing);
                });
    }

    // Eliminar una póliza
    public boolean eliminarPorClave(UUID clave) {
        if (polizaRepository.existsById(clave)) {
            polizaRepository.deleteById(clave);
            return true;
        }
        return false;
    }

}
