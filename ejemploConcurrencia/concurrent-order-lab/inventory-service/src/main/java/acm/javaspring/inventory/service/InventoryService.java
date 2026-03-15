package acm.javaspring.inventory.service;


import acm.javaspring.shared.dto.inventory.InventoryCheckRequest;
import acm.javaspring.shared.dto.inventory.InventoryReserveRequest;
import acm.javaspring.shared.dto.inventory.InventoryResponse;
import acm.javaspring.shared.dto.inventory.InventoryStockResponse;

import java.util.Map;

public interface InventoryService {


    InventoryStockResponse getStock(String productId);

    InventoryResponse checkAvailability(InventoryCheckRequest request);

    InventoryResponse reserve(InventoryReserveRequest request);

    InventoryResponse release(InventoryReserveRequest request);

    Map<String, Integer> getInventorySnapshot();
    void resetInventory();
}
