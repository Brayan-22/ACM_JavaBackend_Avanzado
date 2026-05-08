package com.acm.edd.controller;

import com.acm.edd.dto.OrderRequest;
import com.acm.edd.model.Order;
import com.acm.edd.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Órdenes", description = "Endpoints para la gestión de órdenes en el sistema EDD")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Crear orden", description = "Crea una orden síncronamente y emite un evento asíncrono.")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request) {
        // En un flujo EDD, la creación síncrona solo asegura la persistencia inicial
        // y la delegación de responsabilidades a una cola.
        Order createdOrder = orderService.createOrder(request);
        // Responder rápido al cliente: 201 Created.
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Devuelve todas las órdenes de la base de datos.")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar orden por ID", description = "Permite ver cómo el estado de la orden cambia (Consistencia Eventual).")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }
}
