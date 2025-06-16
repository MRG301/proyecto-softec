package mx.edu.uacm.is.slt.as.sistemapolizas.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.repository.PolizaRepository;
import mx.edu.uacm.is.slt.as.sistemapolizas.service.PolizaService;
import mx.edu.uacm.is.slt.as.sistemapolizas.service.SincronizacionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService service;
    private final PolizaRepository polizaRepo;
    private final SincronizacionService syncService;

    // GET /polizas  listado todas las polizas
    @GetMapping
    public ResponseEntity<List<Poliza>> getTodas() {
        return ResponseEntity.ok(service.buscarTodas());
    }

    // GET /polizas/cliente/{curp}
    @GetMapping("/cliente/{curp}")
    public ResponseEntity<List<Poliza>> getPorCurp(@PathVariable String curp) {
        return ResponseEntity.ok(service.buscarPorCurp(curp));
    }

    // GET /polizas/tipo/{tipo}
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Poliza>> getPorTipo(@PathVariable Integer tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    // GET /polizas/c/{n}/{p}[/{s}]
    @GetMapping({"/c/{n}/{p}", "/c/{n}/{p}/{s}"})
    public ResponseEntity<List<Poliza>> getPorNombreAsegurado(
            @PathVariable("n") String nombres,
            @PathVariable("p") String primerApellido,
            @PathVariable(value = "s", required = false) String segundoApellido) {

        List<Poliza> lista = (segundoApellido == null)
                ? service.buscarPorNombreAsegurado(nombres, primerApellido)
                : service.buscarPorNombreAsegurado(nombres, primerApellido, segundoApellido);
        return ResponseEntity.ok(lista);
    }

    // GET /polizas/b/{n}/{p}[/{s}]
    @GetMapping({"/b/{n}/{p}", "/b/{n}/{p}/{s}"})
    public ResponseEntity<List<Poliza>> getPorNombreBeneficiario(
            @PathVariable("n") String nombres,
            @PathVariable("p") String primerApellido,
            @PathVariable(value = "s", required = false) String segundoApellido) {

        List<Poliza> lista = (segundoApellido == null)
                ? service.buscarPorNombreBeneficiario(nombres, primerApellido)
                : service.buscarPorNombreBeneficiario(nombres, primerApellido, segundoApellido);
        return ResponseEntity.ok(lista);
    }

    // GET /polizas/b/fecha/{fecha}
    @GetMapping("/b/fecha/{fecha}")
    public ResponseEntity<List<Poliza>> getPorFechaBeneficiario(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        return ResponseEntity.ok(service.buscarPorFechaBeneficiario(fecha));
    }

    // GET /polizas/{clave} para recuperar una sola poliza - Revisar
    @GetMapping("/{clave}")
    public ResponseEntity<Poliza> getPorClave(@PathVariable UUID clave) {
        return service.buscarPorClave(clave)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /polizas   crea nueva poliza local + remoto
    @PostMapping
    public ResponseEntity<Poliza> crear(@RequestBody Poliza nueva) {
        Poliza g = syncService.crearOActualizarPolizaLocal(nueva);
        return ResponseEntity
                .created(URI.create("/polizas/" + g.getClave()))
                .body(g);
    }

    // PUT /polizas/{clave}   actualiza local + remoto
    @PutMapping("/{clave}")
    public ResponseEntity<Poliza> actualizar(
            @PathVariable UUID clave,
            @RequestBody Poliza datos) {

        if (!polizaRepo.existsById(clave)) {
            return ResponseEntity.notFound().build();
        }
        datos.setClave(clave);
        Poliza actual = syncService.crearOActualizarPolizaLocal(datos);
        return ResponseEntity.ok(actual);
    }

    // DELETE /polizas/{clave} borra en local y remoto - revisar
    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> borrar(@PathVariable UUID clave) {
        if (!polizaRepo.existsById(clave)) {
            return ResponseEntity.notFound().build();
        }
        syncService.eliminarPolizaLocal(clave);
        return ResponseEntity.noContent().build();
    }
}
