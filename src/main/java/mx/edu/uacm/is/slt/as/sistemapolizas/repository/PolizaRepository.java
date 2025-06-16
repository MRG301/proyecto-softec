package mx.edu.uacm.is.slt.as.sistemapolizas.repository;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PolizaRepository extends JpaRepository<Poliza, UUID> {

    List<Poliza> findByCurpCliente(String curpCliente);
    List<Poliza> findByTipo(Integer tipo);

    @Query("""
        SELECT p FROM Poliza p 
        WHERE p.curpCliente IN (
            SELECT c.curp
            FROM Cliente c
            WHERE c.nombres = :n 
              AND c.primerApellido = :p 
              AND (:s IS NULL OR c.segundoApellido = :s)
        )""")
    List<Poliza> buscarPorNombreAsegurado(@Param("n") String nombres,
            @Param("p") String primerApellido, @Param("s") String segundoApellido);

    @Query("""
        SELECT p FROM Poliza p 
        WHERE p.clave IN (
            SELECT b.id.clavePoliza
            FROM BeneficiarioPoliza b
            WHERE b.id.nombres = :n 
              AND b.id.primerApellido = :p 
              AND (:s IS NULL OR b.id.segundoApellido = :s)
        )""")
    List<Poliza> buscarPorNombreBeneficiario(@Param("n") String nombres,
            @Param("p") String primerApellido, @Param("s") String segundoApellido);

    @Query("""
        SELECT p FROM Poliza p 
        WHERE p.clave IN (
            SELECT b.id.clavePoliza 
            FROM BeneficiarioPoliza b 
            WHERE b.id.fechaNacimiento = :f
        )""")
    List<Poliza> buscarPorFechaBeneficiario(@Param("f") LocalDate fecha);
}