package mx.edu.uacm.is.slt.as.sistemapolizas.integration;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OwnerClienteClient {
/**
    private final RestTemplate rt;
    private final String base;

    public OwnerClienteClient(RestTemplate rt,
                              @Value("${owner.api.url}") String ownerApiUrl) {
        this.rt = rt;
        this.base = ownerApiUrl;
    }

    // GET /cliente/{curp}
    public Cliente getCliente(String curp) {
        return rt.getForObject(base + "/cliente/{curp}", Cliente.class, curp);
    }

    // POST /cliente/{curp}/{direccion}/{fecha_nacimiento}/{nombres}/{primer_apellido}/{segundo_apellido}
    public Cliente createCliente(Cliente c) {
        String url = String.format("%s/cliente/%s/%s/%s/%s/%s/%s",
                base,
                c.getCurp(),
                c.getDireccion(),
                c.getFechaNacimiento(),
                c.getNombres(),
                c.getPrimerApellido(),
                c.getSegundoApellido() == null ? "" : c.getSegundoApellido()
        );
        return rt.postForObject(url, null, Cliente.class);
    }

    // PUT /cliente/{curp}/{direccion}/{fecha_nacimiento}/{nombres}/{primer_apellido}/{segundo_apellido}
    public void updateCliente(Cliente c) {
        String url = String.format("%s/cliente/%s/%s/%s/%s/%s/%s",
                base,
                c.getCurp(),
                c.getDireccion(),
                c.getFechaNacimiento(),
                c.getNombres(),
                c.getPrimerApellido(),
                c.getSegundoApellido() == null ? "" : c.getSegundoApellido()
        );
        rt.put(url, null);
    }
*/
}
