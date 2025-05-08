package mx.edu.uacm.is.slt.as.sistemapolizas.controller;

import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> todos = clienteService.buscarTodos();
        return ResponseEntity.ok(todos);
    }

    // Obtener un cliente por CURP
    @GetMapping("/{curp}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String curp) {
        return clienteService.buscarPorCurp(curp)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevo) {
        Cliente creado = clienteService.guardar(nuevo);
        URI ubicacion = URI.create("/clientes/" + creado.getCurp());
        return ResponseEntity.created(ubicacion).body(creado);
    }

    // Actualizar un cliente existente
    @PutMapping("/{curp}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable String curp,
            @RequestBody Cliente actualizado
    ) {
        return clienteService.actualizar(curp, actualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un cliente por CURP
    @DeleteMapping("/{curp}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String curp) {
        boolean ok = clienteService.eliminarPorCurp(curp);
        return ok
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}