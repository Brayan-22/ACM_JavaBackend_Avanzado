package acm.javaspring.shared.dto.order;


import acm.javaspring.shared.enums.OrderStatus;

public record OrderResponse(
        String orderId,
        OrderStatus status,
        String message
) {
}
