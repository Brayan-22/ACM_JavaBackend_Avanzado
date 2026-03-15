package acm.javaspring.shared.dto.inventory;

public record InventoryStockResponse(
        String productId,
        int availableStock
) {
}