package com.acm.edd.controller;

import com.acm.edd.messaging.consumer.InventoryConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RestController
@RequestMapping("/simulate")
@Tag(name = "Simluar fallos", description = "Endpoints para inyectar fallos de prueba en el ecosistema EDD")
public class SimulationController {

    // Endpoint didáctico: POST /simulate/failure/inventory?fail=true
    @PostMapping("/failure/inventory")
    @Operation(summary = "Forzar fallo en Inventario", description = "Al habilitarse, el próximo mensaje que reciba el InventoryConsumer va a explotar, lo que permite demostrar reintentos (Retries) a los estudiantes.")
    public ResponseEntity<String> toggleInventoryFailure(@RequestParam(defaultValue = "true") boolean fail) {
        InventoryConsumer.simulateFailure = fail;
        log.warn("=== SIMULADOR DE FALLOS EN INVENTARIO CAMBIADO A: {} ===", fail);

        String message = fail
                ? "Fallo activado. El próximo mensaje al inventario fallará y activará los reintentos (Retries)."
                : "Fallo desactivado. El consumidor de inventario operará normalmente.";

        return ResponseEntity.ok(message);
    }
}
