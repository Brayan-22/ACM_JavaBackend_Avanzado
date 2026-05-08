package com.acm.shippingservice.consumer;

import com.acm.shippingservice.config.RabbitConfig;
import com.acm.shippingservice.service.ShippingService;
import com.acm.shippingservice.shared.PaymentProcessedEvent;
import com.acm.shippingservice.shared.ShipmentScheduledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentSuccessListener {

    private final ShippingService shippingService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfig.PAYMENT_SUCCESS_QUEUE)
    public void onPaymentSuccess(PaymentProcessedEvent event) {
        log.info("Recibido PaymentProcessed para orderId: {}", event.getOrderId());
        shippingService.scheduleShipment(event);
        ShipmentScheduledEvent eventSheduled = ShipmentScheduledEvent.builder()
                .orderId(event.getOrderId())
                .timestamp(event.getTimestamp())
                .trackingNumber(UUID.randomUUID().toString())
                .build();
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "shipping.scheduled", eventSheduled);
    }
}