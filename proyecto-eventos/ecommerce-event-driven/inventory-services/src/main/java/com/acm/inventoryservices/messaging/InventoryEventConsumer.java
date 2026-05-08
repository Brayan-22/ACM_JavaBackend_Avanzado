package com.acm.inventoryservices.messaging;

import com.acm.inventoryservices.config.RabbitConfig;
import com.acm.inventoryservices.service.InventoryService;
import com.acm.inventoryservices.shared.OrderPlacedEvent;
import com.acm.inventoryservices.shared.OutOfStockEvent;
import com.acm.inventoryservices.shared.PaymentFailedEvent;
import com.acm.inventoryservices.shared.StockReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.acm.inventoryservices.config.RabbitConfig.EXCHANGE;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryEventConsumer {
    private final InventoryService inventoryService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfig.INVENTORY_ORDER_PLACED_QUEUE)
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Recibido OrderPlaced: {}", event.getOrderId());
        boolean success = inventoryService.reserveStock(event.getOrderId(), event.getItems());
        if (success) {
            // Publicar StockReserved
            StockReservedEvent reservedEvent = new StockReservedEvent();
            reservedEvent.setEventType("StockReserved");
            reservedEvent.setOrderId(event.getOrderId());
            reservedEvent.setItems(event.getItems());
            reservedEvent.setTimestamp(LocalDateTime.now());

            rabbitTemplate.convertAndSend(EXCHANGE, "inventory.reserved", reservedEvent);
            log.info("Publicado StockReserved para {}", event.getOrderId());
        } else {
            // Publicar OutOfStock
            OutOfStockEvent outEvent = new OutOfStockEvent();
            outEvent.setEventType("OutOfStock");
            outEvent.setOrderId(event.getOrderId());
            outEvent.setTrace("Producto agotado");
            outEvent.setTimestamp(LocalDateTime.now());

            rabbitTemplate.convertAndSend(EXCHANGE, "inventory.out", outEvent);
            log.info("Publicado OutOfStock para {}", event.getOrderId());
        }
    }

    @RabbitListener(queues = RabbitConfig.INVENTORY_PAYMENT_FAILED_QUEUE)
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.info("Recibido PaymentFailed: {}", event.getOrderId());
        inventoryService.releaseStock(event.getOrderId());
    }

}
