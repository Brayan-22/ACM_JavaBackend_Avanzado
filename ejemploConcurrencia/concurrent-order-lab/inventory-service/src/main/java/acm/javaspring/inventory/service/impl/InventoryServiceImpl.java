package acm.javaspring.inventory.service.impl;

import acm.javaspring.inventory.service.InventoryService;
import acm.javaspring.inventory.store.InMemoryInventoryStore;
import acm.javaspring.shared.dto.inventory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InMemoryInventoryStore store;
    public InventoryServiceImpl(InMemoryInventoryStore store) {
        this.store = store;
    }
    public InventoryStockResponse getStock(String productId) {
        return new InventoryStockResponse(productId, store.getStock(productId));
    }

    @Override
    public InventoryResponse checkAvailability(InventoryCheckRequest request) {
        for (InventoryItemRequest item : request.items()) {
            if (!store.hasEnoughStock(item.productId(), item.quantity())) {
                return new InventoryResponse(
                        false,
                        "Insufficient stock for product: " + item.productId()
                );
            }
        }

        return new InventoryResponse(true, "Stock available");
    }

    @Override
    public InventoryResponse reserve(InventoryReserveRequest request) {
        for (InventoryItemRequest item : request.items()) {
            if (!store.hasEnoughStock(item.productId(), item.quantity())) {
                return new InventoryResponse(
                        false,
                        "Insufficient stock for product: " + item.productId()
                );
            }
        }

        for (InventoryItemRequest item : request.items()) {
            boolean reserved = store.reserve(item.productId(), item.quantity());
            if (!reserved) {
                rollbackReservations(request);
                return new InventoryResponse(
                        false,
                        "Could not reserve stock for product: " + item.productId()
                );
            }
        }

        return new InventoryResponse(
                true,
                "Stock reserved successfully for order: " + request.orderId()
        );
    }

    @Override
    public InventoryResponse release(InventoryReserveRequest request) {
        for (InventoryItemRequest item : request.items()) {
            store.release(item.productId(), item.quantity());
        }

        return new InventoryResponse(
                true,
                "Stock released successfully for order: " + request.orderId()
        );
    }

    @Override
    public Map<String, Integer> getInventorySnapshot() {
        return store.snapshot();
    }

    @Override
    public void resetInventory() {
        store.reset();
    }

    private void rollbackReservations(InventoryReserveRequest request) {
        for (InventoryItemRequest item : request.items()) {
            store.release(item.productId(), item.quantity());
        }
    }
}
