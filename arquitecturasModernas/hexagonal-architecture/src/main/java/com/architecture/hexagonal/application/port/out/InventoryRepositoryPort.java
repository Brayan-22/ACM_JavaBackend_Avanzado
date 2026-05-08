package com.architecture.hexagonal.application.port.out;

import com.architecture.hexagonal.domain.model.Inventory;
import java.util.Optional;

public interface InventoryRepositoryPort {
    Optional<Inventory> findByProductId(Long productId);
    void save(Inventory inventory);
}
