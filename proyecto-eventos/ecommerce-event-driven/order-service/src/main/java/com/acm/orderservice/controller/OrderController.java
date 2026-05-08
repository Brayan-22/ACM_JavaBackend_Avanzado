package com.acm.orderservice.controller;

import com.acm.orderservice.Constants;
import com.acm.orderservice.config.OrderRabbitConfig;
import com.acm.orderservice.model.OrderItemRequest;
import com.acm.orderservice.model.OrderRequest;
import com.acm.orderservice.model.OrderStatus;
import com.acm.orderservice.persistence.entity.Order;
import com.acm.orderservice.service.OrderService;
import com.acm.orderservice.shared.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASEAPI)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.accepted()
                .body(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }
}
