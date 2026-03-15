package acm.javaspring.shared.dto.customer;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CustomerValidationRequest(
        @NotBlank(message = "customerId is required")
        String customerId,

        @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than 0")
        BigDecimal amount
) {
}