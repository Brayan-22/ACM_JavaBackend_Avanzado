package com.acm.inventoryservices.service;

import com.acm.inventoryservices.shared.OrderItem;

import java.util.List;

public interface InventoryService {
    boolean reserveStock(String orderId, List<OrderItem> items);
    void releaseStock(String orderId);
}
