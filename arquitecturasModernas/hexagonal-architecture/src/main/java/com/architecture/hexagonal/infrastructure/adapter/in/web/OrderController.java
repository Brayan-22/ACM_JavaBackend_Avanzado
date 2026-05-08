package com.architecture.hexagonal.infrastructure.adapter.in.web;

import com.architecture.hexagonal.application.port.in.CancelOrderUseCase;
import com.architecture.hexagonal.application.port.in.CreateOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hexagonal/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, CancelOrderUseCase cancelOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderUseCase.OrderDto createOrder(@RequestBody CreateOrderUseCase.OrderCommand command) {
        return createOrderUseCase.createOrder(command);
    }

    @PostMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable Long id) {
        cancelOrderUseCase.cancelOrder(id);
    }
}
