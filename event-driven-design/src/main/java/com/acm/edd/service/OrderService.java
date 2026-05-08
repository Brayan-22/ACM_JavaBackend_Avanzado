package com.acm.edd.service;

import com.acm.edd.dto.OrderRequest;
import com.acm.edd.messaging.event.OrderCreatedEvent;
import com.acm.edd.messaging.publisher.OrderEventPublisher;
import com.acm.edd.model.Order;
import com.acm.edd.model.OrderItem;
import com.acm.edd.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    @Transactional
    public Order createOrder(OrderRequest request) {
        log.info("Iniciando creación síncrona de orden para el cliente: {}", request.getCustomerId());

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());

        request.getItems().forEach(itemRequest -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemRequest.getProductId());
            orderItem.setQuantity(itemRequest.getQuantity());
            order.addItem(orderItem);
        });

        // 1. Guardar en BD (Síncrono)
        Order savedOrder = orderRepository.save(order);
        log.info("Orden guardada localmente con ID: {} y estado: {}", savedOrder.getId(), savedOrder.getStatus());

        // 2. Crear DTO del Evento y asignarle un ID único para la Idempotencia
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString()) // ID Único de este suceso
                .orderId(savedOrder.getId())
                .customerId(savedOrder.getCustomerId())
                .createdAt(savedOrder.getCreatedAt())
                .build();

        // 3. Publicar Evento (Asíncrono respecto al procesamiento posterior)
        eventPublisher.publishOrderCreatedEvent(event);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
