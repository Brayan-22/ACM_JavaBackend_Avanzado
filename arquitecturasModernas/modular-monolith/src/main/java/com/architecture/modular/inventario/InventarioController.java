package com.architecture.modular.inventario;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modular/inventario")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService service;

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<?> findByProductoId(@PathVariable Long productoId) {
        return service.findByProductoId(productoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Inventario inventario) {
        return ResponseEntity.ok(service.save(inventario));
    }

    @PatchMapping("/producto/{productoId}")
    public ResponseEntity<?> updateStock(@PathVariable Long productoId, @RequestBody Inventario inventario) {
        return service.updateStock(productoId, inventario.getStock())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
