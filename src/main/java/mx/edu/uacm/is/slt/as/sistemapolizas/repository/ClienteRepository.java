package mx.edu.uacm.is.slt.as.sistemapolizas.repository;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

    // buscar por nombre + primer apellido y un posible segundo apellido
    List<Cliente> findByNombresIgnoreCaseAndPrimerApellidoIgnoreCase(String n, String p);

    List<Cliente> findByNombresIgnoreCaseAndPrimerApellidoIgnoreCaseAndSegundoApellidoIgnoreCase(
            String n,String p,String s);
}
