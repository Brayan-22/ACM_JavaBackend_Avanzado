package com.acm.orderservice.service;

import com.acm.orderservice.config.OrderRabbitConfig;
import com.acm.orderservice.model.OrderRequest;
import com.acm.orderservice.model.OrderStatus;
import com.acm.orderservice.persistence.entity.Order;
import com.acm.orderservice.persistence.entity.OrderItem;
import com.acm.orderservice.persistence.repository.OrderJpaRepository;
import com.acm.orderservice.shared.OrderItemEvent;
import com.acm.orderservice.shared.OrderPlacedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderJpaRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public Order createOrder(OrderRequest request) {
        // 1. Crear entidad Order
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        // 2. Añadir items
        request.getItems().forEach(item -> {
            OrderItem orderItem = OrderItem.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .build();
            order.addItem(orderItem);
        });

        // 3. Guardar en BD
        orderRepository.save(order);

        // 4. Construir evento y publicar
        List<OrderItemEvent> itemEvents = order.getItems().stream()
                .map(oi -> new OrderItemEvent(oi.getProductId(), oi.getQuantity()))
                .collect(Collectors.toList());

        OrderPlacedEvent event = new OrderPlacedEvent(order.getId(), order.getCustomerId(), itemEvents);
        log.info("Publicando OrderPlaced para orderId: {}", order.getId());
        rabbitTemplate.convertAndSend(OrderRabbitConfig.EXCHANGE, OrderRabbitConfig.ORDER_PLACED_RK, event);

        return order;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Transactional
    public void cancelOrder(String orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            log.info("Pedido {} cancelado", orderId);
        });
    }
}
