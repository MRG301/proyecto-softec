package mx.edu.uacm.is.slt.as.sistemapolizas.controller;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.service.PolizaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService polizaService;

    @Autowired
    public PolizaController(PolizaService polizaService) {
        this.polizaService = polizaService;
    }

    // Listar todas las pólizas
    @GetMapping
    public ResponseEntity<List<Poliza>> listarTodas() {
        List<Poliza> todas = polizaService.buscarTodas();
        return ResponseEntity.ok(todas);
    }

    // Obtener una póliza por UUID
    @GetMapping("/{clave}")
    public ResponseEntity<Poliza> obtenerPorClave(@PathVariable UUID clave) {
        return polizaService.buscarPorClave(clave)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva póliza
    @PostMapping
    public ResponseEntity<Poliza> crearPoliza(@RequestBody Poliza nueva) {
        Poliza creada = polizaService.guardar(nueva);
        URI location = URI.create("/polizas/" + creada.getClave());
        return ResponseEntity.created(location).body(creada);
    }

    // Actualizar una póliza existente
    @PutMapping("/{clave}")
    public ResponseEntity<Poliza> actualizarPoliza(
            @PathVariable UUID clave,
            @RequestBody Poliza actualizada
    ) {
        return polizaService.actualizar(clave, actualizada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una póliza
    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> eliminarPoliza(@PathVariable UUID clave) {
        if (polizaService.eliminarPorClave(clave)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}