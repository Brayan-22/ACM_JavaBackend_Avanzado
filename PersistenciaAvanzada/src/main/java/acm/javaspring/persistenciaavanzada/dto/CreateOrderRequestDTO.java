package acm.javaspring.persistenciaavanzada.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDTO(
        @NotNull Long customerId,
        @NotEmpty List<@Valid CreateOrderItemRequestDTO> items
) {
}