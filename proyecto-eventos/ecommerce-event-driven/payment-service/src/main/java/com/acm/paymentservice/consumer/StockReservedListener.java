package com.acm.paymentservice.consumer;


import com.acm.paymentservice.config.RabbitConfig;
import com.acm.paymentservice.service.PaymentService;
import com.acm.paymentservice.shared.StockReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockReservedListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = RabbitConfig.STOCK_RESERVED_QUEUE)
    public void onStockReserved(StockReservedEvent event) {
        log.info("Recibido StockReserved para orderId: {}", event.getOrderId());
        paymentService.processPayment(event);
    }
}