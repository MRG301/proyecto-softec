package mx.edu.uacm.is.slt.as.sistemapolizas.repository;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PolizaRepository extends JpaRepository<Poliza, UUID> {
}
