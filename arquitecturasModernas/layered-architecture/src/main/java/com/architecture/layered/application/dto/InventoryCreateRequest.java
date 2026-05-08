package com.architecture.layered.application.dto;

public record InventoryCreateRequest(Long productId, int quantity) {
}
