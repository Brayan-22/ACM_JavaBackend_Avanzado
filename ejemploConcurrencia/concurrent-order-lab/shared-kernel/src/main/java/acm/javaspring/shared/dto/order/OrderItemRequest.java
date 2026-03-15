package acm.javaspring.shared.dto.order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OrderItemRequest(
        @NotBlank(message = "productId is required")
        String productId,

        @Min(value = 1, message = "quantity must be greater than 0")
        int quantity
) {
}