package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPolizaId;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.BeneficiarioPolizaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiarioPolizaService {

    private final BeneficiarioPolizaRepository repository;

    @Autowired
    public BeneficiarioPolizaService(BeneficiarioPolizaRepository repository) {
        this.repository = repository;
    }

    public List<BeneficiarioPoliza> buscarTodos() {
        return repository.findAll();
    }

    public Optional<BeneficiarioPoliza> buscarPorId(BeneficiarioPolizaId id) {
        return repository.findById(id);
    }

    @Transactional
    public BeneficiarioPoliza guardar(BeneficiarioPoliza nuevo) {
        return repository.save(nuevo);
    }

    @Transactional
    public Optional<BeneficiarioPoliza> actualizar(BeneficiarioPoliza actualizado) {
        BeneficiarioPolizaId id = actualizado.getId();
        return repository.findById(id)
                .map(existing -> {
                    existing.setPorcentaje(actualizado.getPorcentaje());
                    return repository.save(existing);
                });
    }

    @Transactional
    public boolean eliminarPorId(BeneficiarioPolizaId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
