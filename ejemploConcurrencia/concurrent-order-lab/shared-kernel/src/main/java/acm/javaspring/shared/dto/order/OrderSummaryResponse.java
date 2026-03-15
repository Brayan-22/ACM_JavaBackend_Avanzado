package acm.javaspring.shared.dto.order;

import acm.javaspring.shared.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderSummaryResponse(
        String orderId,
        String customerId,
        BigDecimal totalAmount,
        OrderStatus status
) {
}