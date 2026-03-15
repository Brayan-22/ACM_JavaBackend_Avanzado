package acm.javaspring.order.service;

import acm.javaspring.shared.dto.order.OrderRequest;
import acm.javaspring.shared.dto.order.OrderResponse;
import acm.javaspring.shared.dto.order.OrderSummaryResponse;

import java.util.List;

public interface OrderProcessingService {
    OrderResponse processOrder(OrderRequest request);

    OrderResponse getOrderById(String orderId);

    List<OrderSummaryResponse> getAllOrders();
}
