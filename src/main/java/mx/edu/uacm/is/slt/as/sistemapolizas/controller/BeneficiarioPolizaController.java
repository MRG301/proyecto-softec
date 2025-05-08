package mx.edu.uacm.is.slt.as.sistemapolizas.controller;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPoliza;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.BeneficiarioPolizaId;
import mx.edu.uacm.is.slt.as.sistemapolizas.service.BeneficiarioPolizaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/beneficiarios")
public class BeneficiarioPolizaController {

    private final BeneficiarioPolizaService service;

    @Autowired
    public BeneficiarioPolizaController(BeneficiarioPolizaService service) {
        this.service = service;
    }

    // Listar todos los beneficiarios
    @GetMapping
    public ResponseEntity<List<BeneficiarioPoliza>> listarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    /**
     * Obtener por su clave compuesta
     * ** darle una revisa
     */
    @GetMapping("/{clavePoliza}/{nombres}/{primerApellido}/{segundoApellido}/{fechaNacimiento}")
    public ResponseEntity<BeneficiarioPoliza> obtenerPorId(
            @PathVariable UUID clavePoliza,
            @PathVariable String nombres,
            @PathVariable String primerApellido,
            @PathVariable String segundoApellido,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento
    ) {
        BeneficiarioPolizaId id = new BeneficiarioPolizaId(
                clavePoliza, nombres, primerApellido, segundoApellido, fechaNacimiento
        );
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo beneficiario
    @PostMapping
    public ResponseEntity<BeneficiarioPoliza> crear(@RequestBody BeneficiarioPoliza nuevo) {
        BeneficiarioPoliza creado = service.guardar(nuevo);
        return ResponseEntity.status(201).body(creado);
    }

    // Actualizar un beneficiario existente se asume que el JSON incluye la PrimaryKey compuesta
    @PutMapping
    public ResponseEntity<BeneficiarioPoliza> actualizar(@RequestBody BeneficiarioPoliza actualizado) {
        return service.actualizar(actualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un beneficiario por su clave compuesta
     * Revisar
     * Misma ruta que el GET por ID
     */
    @DeleteMapping("/{clavePoliza}/{nombres}/{primerApellido}/{segundoApellido}/{fechaNacimiento}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID clavePoliza,
            @PathVariable String nombres,
            @PathVariable String primerApellido,
            @PathVariable String segundoApellido,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento
    ) {
        BeneficiarioPolizaId id = new BeneficiarioPolizaId(
                clavePoliza, nombres, primerApellido, segundoApellido, fechaNacimiento
        );
        if (service.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}