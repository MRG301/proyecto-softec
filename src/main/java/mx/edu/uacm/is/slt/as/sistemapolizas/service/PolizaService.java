package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.PolizaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PolizaService {

    private final SincronizacionService syncService;
    private final PolizaRepository repo;

    @Autowired
    public PolizaService(PolizaRepository repo, SincronizacionService syncService) {
        this.repo = repo;
        this.syncService = syncService;
    }

    public List<Poliza> buscarTodas() {
        return repo.findAll();
    }

    public Optional<Poliza> buscarPorClave(UUID c) {
        return repo.findById(c);
    }

    public Poliza guardar(Poliza p) {
        return syncService.crearOActualizarPolizaLocal(p);
    }

    public boolean eliminarPorClave(UUID c) {
        if (repo.existsById(c)) {
            syncService.eliminarPolizaLocal(c);
            return true;
        }
        return false;
    }

    // métodos de búsqueda que delegan al repositorio local
    public List<Poliza> buscarPorCurp(String curp) {
        return repo.findByCurpCliente(curp);
    }
    public List<Poliza> buscarPorTipo(Integer tipo) {
        return repo.findByTipo(tipo);
    }
    public List<Poliza> buscarPorNombreAsegurado(String n, String p) {
        return List.of();
    }
    public List<Poliza> buscarPorNombreAsegurado(String n, String p, String s) {
        return List.of();
    }
    public List<Poliza> buscarPorNombreBeneficiario(String n, String p) {
        return List.of();
    }
    public List<Poliza> buscarPorNombreBeneficiario(String n, String p, String s) {
        return List.of();
    }
    public List<Poliza> buscarPorFechaBeneficiario(LocalDate f) {
        return List.of();
    }
}
