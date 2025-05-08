package mx.edu.uacm.is.slt.as.sistemapolizas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService polizaService;

    @Autowired
    public PolizaController(PolizaService polizaService) {
        this.polizaService = polizaService;
    }

    @GetMapping("/{clave}")
    public ResponseEntity<Poliza> obtenerPoliza(@PathVariable UUID clave) {
        return polizaService.buscarPorClave(clave)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Poliza> crearPoliza(@RequestBody Poliza nuevaPoliza) {
        Poliza creada = polizaService.guardar(nuevaPoliza);
        URI ubicacion = URI.create("/polizas/" + creada.getClave());
        return ResponseEntity.created(ubicacion).body(creada);
    }

    @PutMapping("/{clave}")
    public ResponseEntity<Poliza> actualizarPoliza(@PathVariable UUID clave,
                                                   @RequestBody Poliza polizaActualizada) {
        return polizaService.actualizar(clave, polizaActualizada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> eliminarPoliza(@PathVariable UUID clave) {
        if (polizaService.eliminarPorClave(clave)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
