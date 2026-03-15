package acm.javaspring.order.controller;

import acm.javaspring.order.service.OrderProcessingService;
import acm.javaspring.shared.dto.order.OrderRequest;
import acm.javaspring.shared.dto.order.OrderResponse;
import acm.javaspring.shared.dto.order.OrderSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    public OrderController(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> processOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderProcessingService.processOrder(request);

        if (response.status().name().equals("COMPLETED")) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderProcessingService.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> getAllOrders() {
        return ResponseEntity.ok(orderProcessingService.getAllOrders());
    }
}
