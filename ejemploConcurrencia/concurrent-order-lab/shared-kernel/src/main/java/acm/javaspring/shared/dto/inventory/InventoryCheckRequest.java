package acm.javaspring.shared.dto.inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record InventoryCheckRequest(
        @NotEmpty(message = "items are required")
        List<@Valid InventoryItemRequest> items
) {
}