package acm.javaspring.shared.dto.inventory;

public record InventoryResponse(
        boolean available,
        String message
) {
}