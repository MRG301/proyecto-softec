package mx.edu.uacm.is.slt.as.sistemapolizas.extern;

import lombok.RequiredArgsConstructor;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.BeneficiarioDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.ClienteDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.dto.PolizaDTO;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPolizaId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PolizaExternalClient {

    @Qualifier("polizaWebClient")
    private final WebClient web;

    public void actualizarPolizaRemota(PolizaDTO dto) {
        web.put()
                .uri("/{clave}", dto.clave())
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    // End-points

    public List<PolizaDTO> obtenerTodasLasPolizas() {
        return web.get()
                .uri("/polizas")
                .retrieve()
                .bodyToFlux(PolizaDTO.class)
                .collectList()
                // sincrónico en arranque
                .block();
    }

    public Optional<BeneficiarioDTO> obtenerBeneficiario(BeneficiarioPolizaId id) {
        return web.get()
                .uri("/beneficiario/{f}/{c}/{n}/{p}/{s}",
                        id.getFechaNacimiento(),
                        id.getClavePoliza(),
                        id.getNombres(),
                        id.getPrimerApellido(),
                        id.getSegundoApellido())
                .retrieve()
                .bodyToMono(BeneficiarioDTO.class)
                .blockOptional();
    }

    public ClienteDTO obtenerCliente(String curp) {
        return web.get()
                .uri("/cliente/{curp}", curp)
                .retrieve()
                .bodyToMono(ClienteDTO.class)
                .block();
    }

    // Obtiene todos los beneficiarios de la póliza. Las respuestas HTTP 4xx
    // se transforman en una lista vacía en lugar de provocar una excepción.
    public List<BeneficiarioDTO> obtenerBeneficiarios(UUID clave) {
        return web.get()
                .uri("/poliza/{clave}/beneficiarios", clave)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> Mono.empty())
                .bodyToFlux(BeneficiarioDTO.class)
                .collectList()
                .block();
    }

    // Obtiene todos los beneficiarios de la póliza sin interceptar las respuestas
    // 4xx; cualquier error de este tipo se propaga como excepción.
    public List<BeneficiarioDTO> obtenerBeneficiariosPorPoliza(UUID clavePoliza) {
        return web.get()
                .uri("/beneficiarios/{clave}", clavePoliza)   // una sola variable
                .retrieve()
                .bodyToFlux(BeneficiarioDTO.class)
                .collectList()
                .block();
    }

    public Optional<ClienteDTO> obtenerClientePorCurp(String curp) {
        return Optional.ofNullable(obtenerCliente(curp));
    }

    public void eliminarPolizaRemota(UUID clave) {
        web.delete()
                .uri("/poliza/{clave}", clave)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}