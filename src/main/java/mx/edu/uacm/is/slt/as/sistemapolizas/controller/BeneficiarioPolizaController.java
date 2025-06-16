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

    // obtener un beneficiario por clave compuesta, puede venir con segundoApellido o sin segundoApellido
    @GetMapping(value = {
            "/{clavePoliza}/{nombres}/{primerApellido}/{segundoApellido}/{fechaNacimiento}",
            "/{clavePoliza}/{nombres}/{primerApellido}/{fechaNacimiento}"
    })
    public ResponseEntity<BeneficiarioPoliza> obtenerPorId(
            @PathVariable UUID clavePoliza,
            @PathVariable String nombres,
            @PathVariable String primerApellido,
            @PathVariable(required = false) String segundoApellido,
            // si llega a caer por la segunda ruta, sin segundoApellido
            // Spring asigna a este parametro el valor del path “fechaNacimiento”
            // para distinguir, verificamos si es número de comentarios o no
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento
    ) {
        // si segundoApellido vino con formato de fecha, intercambiamos
        if (segundoApellido != null && segundoApellido.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // Quiere decir que Spring lo metió en el path-variable “segundoApellido” porque
            // coincidió con la segunda ruta (“/.../{primerApellido}/{fechaNacimiento}”), entonces reasignamos
            fechaNacimiento = LocalDate.parse(segundoApellido);
            segundoApellido = null;
        }

        BeneficiarioPolizaId id = new BeneficiarioPolizaId(
                clavePoliza, nombres, primerApellido, segundoApellido, fechaNacimiento
        );
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear un beneficiario nuevo
    @PostMapping
    public ResponseEntity<BeneficiarioPoliza> crear(@RequestBody BeneficiarioPoliza nuevo) {
        BeneficiarioPoliza creado = service.guardar(nuevo);
        return ResponseEntity.status(201).body(creado);
    }

    // actualizar un beneficiario existente, se asume que JSON incluye el id compuesto completo
    @PutMapping
    public ResponseEntity<BeneficiarioPoliza> actualizar(@RequestBody BeneficiarioPoliza actualizado) {
        return service.actualizar(actualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // eliminar un beneficiario por clave compuesta
    @DeleteMapping(value = {
            "/{clavePoliza}/{nombres}/{primerApellido}/{segundoApellido}/{fechaNacimiento}",
            "/{clavePoliza}/{nombres}/{primerApellido}/{fechaNacimiento}"
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID clavePoliza,
            @PathVariable String nombres,
            @PathVariable String primerApellido,
            @PathVariable(required = false) String segundoApellido,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento
    ) {
        if (segundoApellido != null && segundoApellido.matches("\\d{4}-\\d{2}-\\d{2}")) {
            fechaNacimiento = LocalDate.parse(segundoApellido);
            segundoApellido = null;
        }

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