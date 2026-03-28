package acm.javaspring.persistenciaavanzada.controller;

import acm.javaspring.persistenciaavanzada.dto.CreateOrderRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.OrderResponseDTO;
import acm.javaspring.persistenciaavanzada.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO create(@RequestBody @Valid CreateOrderRequestDTO request) {
        return orderService.createOrder(request);
    }
}