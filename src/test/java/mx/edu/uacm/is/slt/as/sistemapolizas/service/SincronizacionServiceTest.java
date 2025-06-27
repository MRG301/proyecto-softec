package mx.edu.uacm.is.slt.as.sistemapolizas.service;

import mx.edu.uacm.is.slt.as.sistemapolizas.dto.BeneficiarioDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.ClienteDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.PolizaDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.extern.PolizaExternalClient;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.BeneficiarioPolizaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SincronizacionServiceTest {

    @Autowired
    private SincronizacionService service;

    @Autowired
    private BeneficiarioPolizaRepository beneficiarioRepo;


    @MockBean
    private PolizaExternalClient external;

    private BeneficiarioDTO crearBeneficiarioDto(String nombre) {
        return new BeneficiarioDTO(nombre, "P", "S", OffsetDateTime.now(), 50);
    }

    @Test
    void sincronizarTodoReemplazaBeneficiarios() {
        UUID clave = UUID.randomUUID();
        PolizaDTO polizaDto = new PolizaDTO(clave, 1, 100.0, "desc", "CURP1");
        when(external.obtenerTodasLasPolizas()).thenReturn(List.of(polizaDto));
        when(external.obtenerCliente("CURP1")).thenReturn(
                new ClienteDTO("CURP1", "Nom", "P", "S", "dir", OffsetDateTime.now()));
        when(external.obtenerBeneficiariosPorPoliza(clave))
                .thenReturn(List.of(crearBeneficiarioDto("Ana"), crearBeneficiarioDto("Beto")));

        // preexisting beneficiary
        beneficiarioRepo.save(new BeneficiarioPoliza(clave, "Viejo", "B", "S", LocalDate.now(), 100));

        service.sincronizarTodo();

        List<BeneficiarioPoliza> todos = beneficiarioRepo.findByIdClavePoliza(clave);
        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(b -> b.getId().getNombres().equals("Ana")));
        assertTrue(todos.stream().anyMatch(b -> b.getId().getNombres().equals("Beto")));
    }

    @Test
    void sincronizarTodoBorraBeneficiariosSiNoHayRemotos() {
        UUID clave = UUID.randomUUID();
        PolizaDTO polizaDto = new PolizaDTO(clave, 1, 100.0, "desc", "CURP2");
        when(external.obtenerTodasLasPolizas()).thenReturn(List.of(polizaDto));
        when(external.obtenerCliente("CURP2")).thenReturn(
                new ClienteDTO("CURP2", "Nom", "P", "S", "dir", OffsetDateTime.now()));
        when(external.obtenerBeneficiariosPorPoliza(clave)).thenReturn(List.of());

        beneficiarioRepo.save(new BeneficiarioPoliza(clave, "Viejo", "B", "S", LocalDate.now(), 100));

        service.sincronizarTodo();

        List<BeneficiarioPoliza> todos = beneficiarioRepo.findByIdClavePoliza(clave);
        assertTrue(todos.isEmpty());
    }
}
