package com.architecture.layered.presentation.controller;

import com.architecture.layered.application.dto.InventoryCreateRequest;
import com.architecture.layered.application.service.InventoryService;
import com.architecture.layered.domain.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/layered/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public Inventory getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody InventoryCreateRequest request) {
        return inventoryService.createInventory(request);
    }
}
