package acm.javaspring.persistenciaavanzada.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        Long customerId,
        String status,
        BigDecimal total,
        List<OrderItemDetail> items
) {
    public record OrderItemDetail(
            Long productId,
            String productName,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {}
}