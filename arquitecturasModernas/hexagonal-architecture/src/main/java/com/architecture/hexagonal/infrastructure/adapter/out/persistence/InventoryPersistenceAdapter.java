package com.architecture.hexagonal.infrastructure.adapter.out.persistence;

import com.architecture.hexagonal.application.port.out.InventoryRepositoryPort;
import com.architecture.hexagonal.domain.model.Inventory;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.InventoryJpaEntity;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository.SpringDataInventoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component
public class InventoryPersistenceAdapter implements InventoryRepositoryPort {

    private final SpringDataInventoryRepository repository;

    public InventoryPersistenceAdapter(SpringDataInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Inventory> findByProductId(Long productId) {
        return repository.findByProductId(productId).map(entity -> 
            new Inventory(entity.getId(), entity.getProductId(), entity.getQuantity())
        );
    }

    @Override
    public void save(Inventory inventory) {
        InventoryJpaEntity entity = new InventoryJpaEntity();
        entity.setId(inventory.getId());
        entity.setProductId(inventory.getProductId());
        entity.setQuantity(inventory.getQuantity());
        repository.save(entity);
    }
}
