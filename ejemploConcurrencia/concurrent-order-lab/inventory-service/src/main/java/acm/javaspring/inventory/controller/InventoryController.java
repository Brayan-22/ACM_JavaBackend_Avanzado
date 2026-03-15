package acm.javaspring.inventory.controller;

import acm.javaspring.inventory.service.InventoryService;
import acm.javaspring.shared.dto.inventory.InventoryCheckRequest;
import acm.javaspring.shared.dto.inventory.InventoryReserveRequest;
import acm.javaspring.shared.dto.inventory.InventoryResponse;
import acm.javaspring.shared.dto.inventory.InventoryStockResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryStockResponse> getStock(@PathVariable String productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }

    @PostMapping("/check")
    public ResponseEntity<InventoryResponse> checkAvailability(
            @Valid @RequestBody InventoryCheckRequest request
    ) {
        return ResponseEntity.ok(inventoryService.checkAvailability(request));
    }

    @PostMapping("/reserve")
    public ResponseEntity<InventoryResponse> reserve(
            @Valid @RequestBody InventoryReserveRequest request
    ) {
        InventoryResponse response = inventoryService.reserve(request);

        if (response.available()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/release")
    public ResponseEntity<InventoryResponse> release(
            @Valid @RequestBody InventoryReserveRequest request
    ) {
        return ResponseEntity.ok(inventoryService.release(request));
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        inventoryService.resetInventory();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/snapshot")
    public ResponseEntity<Map<String, Integer>> snapshot() {
        return ResponseEntity.ok(inventoryService.getInventorySnapshot());
    }
}
