package acm.javaspring.shared.dto.inventory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryItemRequest(
        @NotBlank(message = "productId is required")
        String productId,

        @Min(value = 1, message = "quantity must be greater than 0")
        int quantity
) {
}