package com.architecture.bad.controller;

import com.architecture.bad.service.InventoryAllPowerfulService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bad/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryAllPowerfulService godService;

    @PostMapping("/restock")
    public void restock(@RequestParam Long productId, @RequestParam int amount) {
        godService.restockAndActivateProductAndSendNotification(productId, amount);
    }
}
