package com.acm.edd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

    @NotBlank(message = "El customerId no puede estar vacío")
    private String customerId;

    @NotEmpty(message = "La orden debe tener al menos un ítem")
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        @NotBlank(message = "El productId no puede estar vacío")
        private String productId;
        private Integer quantity;
    }
}
