package com.architecture.bad.controller;

import com.architecture.bad.entity.Order;
import com.architecture.bad.entity.OrderItem;
import com.architecture.bad.entity.Product;
import com.architecture.bad.entity.User;
import com.architecture.bad.entity.Inventory;
import com.architecture.bad.repository.InventoryRepository;
import com.architecture.bad.repository.OrderRepository;
import com.architecture.bad.repository.ProductRepository;
import com.architecture.bad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Anti-patrón: Fat Controller.
 * Lógica de negocio pesada, uso directo de repositorios en el controlador,
 * retorno de Entidades de base de datos exponiendo modelo directamente,
 * validaciones acopladas al marco web.
 */
@RestController
@RequestMapping("/bad/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    // Recibe un DTO interno improvisado anónimo (o un map, o la entidad directa).
    // Se usa un "Request" estático dentro del controller para demostrar mala
    // organización.
    public static class OrderRequest {
        public Long userId;
        public List<ItemRequest> items;

        public static class ItemRequest {
            public Long productId;
            public int quantity;
        }
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        // Fallo de validación estructurada: validando a mano.
        // Para eso esta el api validation
        if (request.userId == null) {
            throw new RuntimeException("UserId is required");
        }

        User user = userRepository.findById(request.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreationDate(new Date());
        order.setStatus("CREATED");

        double total = 0.0;

        for (OrderRequest.ItemRequest itemReq : request.items) {
            Product product = productRepository.findById(itemReq.productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            // Regla de negocio en el controller: Producto inactivo
            if (!product.isActive()) {
                throw new RuntimeException("Product is inactive: " + product.getName());
            }

            Inventory inv = inventoryRepository.findByProductId(product.getId());
            if (inv == null || inv.getQuantity() < itemReq.quantity) {
                throw new RuntimeException("Not enough inventory for: " + product.getName());
            }

            // Descontar inventario sin patrón de manejo de stock o eventos, lógica acoplada
            // al controller
            inv.setQuantity(inv.getQuantity() - itemReq.quantity);
            inventoryRepository.save(inv);

            // Armar item
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.quantity);
            item.setPrice(product.getDefaultPrice()); // Precio en el momento

            order.getItems().add(item);
            total += (item.getPrice() * item.getQuantity());
        }

        // Regla: fijar precio
        order.setTotalAmount(total);

        // Guardar la orden y sus items por cascade
        return orderRepository.save(order); // Retorna la entidad JPA directamente (anti-patrón)
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        // Lógica de negocio de estado suelta sin patrón State o encapsulamiento
        if ("PAID".equals(order.getStatus())) {
            throw new RuntimeException("Cannot cancel a paid order");
        }
        order.setStatus("CANCELLED");
        // No se repone el inventario por diseño simplificado o error, otra señal de
        // lógica difusa
        return orderRepository.save(order);
    }
}
