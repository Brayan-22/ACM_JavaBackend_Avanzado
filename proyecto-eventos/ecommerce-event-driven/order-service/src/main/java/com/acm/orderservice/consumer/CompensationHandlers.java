package com.acm.orderservice.consumer;

import com.acm.orderservice.config.OrderRabbitConfig;
import com.acm.orderservice.service.OrderService;
import com.acm.orderservice.shared.OutOfStockEvent;
import com.acm.orderservice.shared.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CompensationHandlers {

    private final OrderService orderService;

    @RabbitListener(queues = OrderRabbitConfig.OUT_OF_STOCK_QUEUE)
    public void onOutOfStock(OutOfStockEvent event) {
        log.info("Recibido OutOfStock para orderId: {}. Reason: {}", event.getOrderId(), event.getReason());
        orderService.cancelOrder(event.getOrderId());
    }

    @RabbitListener(queues = OrderRabbitConfig.PAYMENT_FAILED_QUEUE)
    public void onPaymentFailed(PaymentFailedEvent event) {
        log.info("Recibido PaymentFailed para orderId: {}. Reason: {}", event.getOrderId(), event.getReason());
        orderService.cancelOrder(event.getOrderId());
    }
}
