package acm.javaspring.persistenciaavanzada.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderItemRequestDTO(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity
) {
}