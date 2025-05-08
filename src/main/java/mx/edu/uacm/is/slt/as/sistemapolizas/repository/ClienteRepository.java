package mx.edu.uacm.is.slt.as.sistemapolizas.repository;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
