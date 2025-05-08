package mx.edu.uacm.is.slt.as.sistemapolizas.repository;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPolizaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioPolizaRepository extends JpaRepository<BeneficiarioPoliza, BeneficiarioPolizaId> {
}
