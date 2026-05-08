package com.architecture.layered.application.service;

import com.architecture.layered.application.dto.InventoryCreateRequest;
import com.architecture.layered.domain.model.Inventory;
import com.architecture.layered.domain.model.Product;
import com.architecture.layered.infrastructure.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));
    }

    @Transactional
    public void decreaseInventory(Long productId, int quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.decrease(quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory createInventory(InventoryCreateRequest request) {
        Product product = productService.getProduct(request.productId());
        Inventory inventory = new Inventory(product, request.quantity());
        return inventoryRepository.save(inventory);
    }
}
