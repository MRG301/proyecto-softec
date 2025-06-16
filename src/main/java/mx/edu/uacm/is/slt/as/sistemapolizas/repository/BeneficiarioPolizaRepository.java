package mx.edu.uacm.is.slt.as.sistemapolizas.repository;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPolizaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BeneficiarioPolizaRepository extends JpaRepository<BeneficiarioPoliza, BeneficiarioPolizaId> {

    List<BeneficiarioPoliza> findByIdClavePoliza(UUID clavePoliza);

    // Método para borrar todos los beneficiarios de una póliza
    @Modifying
    @Query("DELETE FROM BeneficiarioPoliza b WHERE b.id.clavePoliza = :clave")
    void deleteAllByIdClavePoliza(@Param("clave") UUID clavePoliza);
}