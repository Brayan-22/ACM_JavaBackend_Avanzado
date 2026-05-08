package com.architecture.vertical.features.orders;

import com.architecture.vertical.features.inventory.InventoryFacade;
import com.architecture.vertical.features.products.ProductFacade;
import com.architecture.vertical.features.users.UserFacade;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vertical/orders")
class OrderController {

    private final OrderRepository orderRepository;
    private final ProductFacade productFacade;
    private final InventoryFacade inventoryFacade;
    private final UserFacade userFacade;

    OrderController(OrderRepository orderRepository,
                    ProductFacade productFacade,
                    InventoryFacade inventoryFacade,
                    UserFacade userFacade) {
        this.orderRepository = orderRepository;
        this.productFacade = productFacade;
        this.inventoryFacade = inventoryFacade;
        this.userFacade = userFacade;
    }

    public record CreateOrderReq(Long userId, List<ItemReq> items) {}
    public record ItemReq(Long productId, int quantity) {}

    @PostMapping
    @Transactional
    public Order createOrder(@RequestBody CreateOrderReq req) {
        if (!userFacade.userExists(req.userId())) {
            throw new RuntimeException("User not found");
        }

        Order order = new Order();
        order.setUserId(req.userId());
        order.setStatus("CREATED");
        
        double total = 0.0;
        
        for (ItemReq item : req.items()) {
            if (!productFacade.isProductActiveAndValid(item.productId())) {
                throw new RuntimeException("Invalid product: " + item.productId());
            }

            // Descuenta inventario. La lógica y tabla viven en el slice de inventario
            inventoryFacade.discountInventory(item.productId(), item.quantity());

            double price = productFacade.getProductPrice(item.productId());
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.productId());
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(price);
            
            order.getItems().add(orderItem);
            total += (price * item.quantity());
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
}
