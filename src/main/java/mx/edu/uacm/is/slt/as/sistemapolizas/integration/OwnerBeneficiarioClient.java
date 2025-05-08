package mx.edu.uacm.is.slt.as.sistemapolizas.integration;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class OwnerBeneficiarioClient {
/**
    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public OwnerBeneficiarioClient(RestTemplate restTemplate,
                                   @Value("${owner.api.url}") String ownerApiUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = ownerApiUrl;
    }

    /**
     * GET /beneficiario/{fecha_nacimiento}/{clave_poliza}/{nombres}/{primer_apellido}/{segundo_apellido}
     *//**
    public BeneficiarioPoliza getBeneficiario(LocalDate fechaNacimiento,
                                              UUID clavePoliza,
                                              String nombres,
                                              String primerApellido,
                                              String segundoApellido) {
        String url = String.format("%s/beneficiario/%s/%s/%s/%s/%s",
                baseUrl,
                fechaNacimiento,                     // yyyy-MM-dd
                clavePoliza,                         // UUID
                nombres,
                primerApellido,
                segundoApellido == null ? "" : segundoApellido
        );
        return restTemplate.getForObject(url, BeneficiarioPoliza.class);
    }

    /**
     * POST /beneficiario/{fecha_nacimiento}/{clave_poliza}/{porcentaje}/{nombres}/{primer_apellido}/{segundo_apellido}
     *//**
    public BeneficiarioPoliza createBeneficiario(BeneficiarioPoliza beneficiario) {
        // extraigo datos de la clave compuesta
        LocalDate fecha = beneficiario.getFechaNacimiento();
        UUID clavePoliza = beneficiario.getPoliza().getClave();
        int porcentaje = beneficiario.getPorcentaje();
        String nombres = beneficiario.getNombres();
        String primer = beneficiario.getPrimerApellido();
        String segundo = beneficiario.getSegundoApellido();

        String url = String.format("%s/beneficiario/%s/%s/%d/%s/%s/%s",
                baseUrl,
                fecha,
                clavePoliza,
                porcentaje,
                nombres,
                primer,
                segundo == null ? "" : segundo
        );
        return restTemplate.postForObject(url, null, BeneficiarioPoliza.class);
    }

    /**
     * DELETE /beneficiario/{fecha_nacimiento}/{clave_poliza}/{nombres}/{primer_apellido}/{segundo_apellido}
     *//**
    public void deleteBeneficiario(LocalDate fechaNacimiento,
                                   UUID clavePoliza,
                                   String nombres,
                                   String primerApellido,
                                   String segundoApellido) {
        String url = String.format("%s/beneficiario/%s/%s/%s/%s/%s",
                baseUrl,
                fechaNacimiento,
                clavePoliza,
                nombres,
                primerApellido,
                segundoApellido == null ? "" : segundoApellido
        );
        restTemplate.delete(url);
    }*/
}