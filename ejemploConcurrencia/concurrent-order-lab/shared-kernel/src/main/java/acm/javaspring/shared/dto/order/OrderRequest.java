package acm.javaspring.shared.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        @NotBlank(message = "orderId is required")
        String orderId,

        @NotBlank(message = "customerId is required")
        String customerId,

        @NotEmpty(message = "items are required")
        List<@Valid OrderItemRequest> items,

        @DecimalMin(value = "0.0", inclusive = false, message = "totalAmount must be greater than 0")
        BigDecimal totalAmount
) {
}
