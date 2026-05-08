package com.architecture.vertical.features.inventory;

import com.architecture.vertical.features.configurations.SenderEmails;
import org.springframework.stereotype.Service;

// --- Facade Implementation ---
@Service
class InventoryFacadeImpl implements InventoryFacade {
    private final SenderEmails senderEmails;
    private final InventoryRepository repository;

    InventoryFacadeImpl(InventoryRepository repository, SenderEmails senderEmails) {
        this.repository = repository;
        this.senderEmails = senderEmails;
    }

    @Override
    public void discountInventory(Long productId, int quantity) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        repository.save(inventory);
    }
}
