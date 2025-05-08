package mx.edu.uacm.is.slt.as.sistemapolizas.integration;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OwnerPolizaClient {
/**
    private final RestTemplate rt;
    private final String base;

    public OwnerPolizaClient(RestTemplate rt,
                             @Value("${owner.api.url}") String ownerApiUrl) {
        this.rt = rt;
        this.base = ownerApiUrl;
    }

    // GET /poliza/{clave}
    public Poliza getPoliza(UUID clave) {
        return rt.getForObject(base + "/poliza/{clave}", Poliza.class, clave);
    }

    // GET /polizas
    public List<Poliza> getAllPolizas() {
        Poliza[] arr = rt.getForObject(base + "/polizas", Poliza[].class);
        return Arrays.asList(arr);
    }

    // GET /polizas/{curp}
    public List<Poliza> getPolizasPorCurp(String curp) {
        Poliza[] arr = rt.getForObject(base + "/polizas/{curp}", Poliza[].class, curp);
        return Arrays.asList(arr);
    }

    // GET /polizas/{tipo}
    public List<Poliza> getPolizasPorTipo(int tipo) {
        Poliza[] arr = rt.getForObject(base + "/polizas/{tipo}", Poliza[].class, tipo);
        return Arrays.asList(arr);
    }

    // GET /polizas/c/{nombres}/{primer_apellido}/{segundo_apellido}
    public List<Poliza> getPolizasPorClienteNombre(String nombres,
                                                   String primerApellido,
                                                   String segundoApellido) {
        Poliza[] arr = rt.getForObject(
                base + "/polizas/c/{nombres}/{primer}/{segundo}",
                Poliza[].class,
                nombres, primerApellido, segundoApellido
        );
        return Arrays.asList(arr);
    }

    // GET /polizas/b/{nombres}/{primer_apellido}/{segundo_apellido}
    public List<Poliza> getPolizasPorBeneficiarioNombre(String nombres,
                                                        String primerApellido,
                                                        String segundoApellido) {
        Poliza[] arr = rt.getForObject(
                base + "/polizas/b/{nombres}/{primer}/{segundo}",
                Poliza[].class,
                nombres, primerApellido, segundoApellido
        );
        return Arrays.asList(arr);
    }

    // GET /polizas/b/{fecha_nacimiento}
    public List<Poliza> getPolizasPorBeneficiarioFecha(String fechaNacimiento) {
        Poliza[] arr = rt.getForObject(
                base + "/polizas/b/{fecha_nacimiento}",
                Poliza[].class,
                fechaNacimiento
        );
        return Arrays.asList(arr);
    }

    // POST /poliza/{clave}/{tipo}/{monto}/{descripcion}/{curp_cliente}
    public Poliza createPoliza(UUID clave,
                               int tipo,
                               BigDecimal monto,
                               String descripcion,
                               String curpCliente) {
        String url = String.format(
                base + "/poliza/%s/%d/%s/%s/%s",
                clave, tipo, monto.toPlainString(), descripcion, curpCliente
        );
        return rt.postForObject(url, null, Poliza.class);
    }

    // PUT /poliza/{clave}/{tipo}/{monto}/{descripcion}/{curp_cliente}
    public void updatePoliza(UUID clave,
                             int tipo,
                             BigDecimal monto,
                             String descripcion,
                             String curpCliente) {
        String url = String.format(
                base + "/poliza/%s/%d/%s/%s/%s",
                clave, tipo, monto.toPlainString(), descripcion, curpCliente
        );
        rt.put(url, null);
    }

    // DELETE /poliza/{clave}
    public void deletePoliza(UUID clave) {
        rt.delete(base + "/poliza/{clave}", clave);
    }
                             */
}
