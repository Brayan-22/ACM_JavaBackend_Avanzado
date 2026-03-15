package acm.javaspring.shared.dto.inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record InventoryReserveRequest(
        @NotBlank(message = "orderId is required")
        String orderId,

        @NotEmpty(message = "items are required")
        List<@Valid InventoryItemRequest> items
) {
}