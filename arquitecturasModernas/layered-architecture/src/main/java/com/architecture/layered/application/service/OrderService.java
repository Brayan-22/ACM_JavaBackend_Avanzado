package com.architecture.layered.application.service;

import com.architecture.layered.application.dto.OrderCreateRequest;
import com.architecture.layered.application.dto.OrderResponse;
import com.architecture.layered.domain.model.Order;
import com.architecture.layered.domain.model.Product;
import com.architecture.layered.domain.model.User;
import com.architecture.layered.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        User user = userService.getUser(request.userId());

        Order order = new Order(user);

        for (OrderCreateRequest.OrderItemRequest itemReq : request.items()) {
            Product product = productService.getProduct(itemReq.productId());

            inventoryService.decreaseInventory(product.getId(), itemReq.quantity());

            // El dominio delega las validaciones de negocio e internamente arma el order
            // item y recalcula totales
            order.addItem(product, itemReq.quantity());
        }

        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Regla encapsulada en la entidad Order
        order.cancel();

        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }
}
