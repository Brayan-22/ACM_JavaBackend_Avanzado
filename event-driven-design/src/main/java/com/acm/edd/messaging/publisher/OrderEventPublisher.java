package com.acm.edd.messaging.publisher;

import com.acm.edd.config.RabbitMQConfig;
import com.acm.edd.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Publicando OrderCreatedEvent para OrderId: {} en Exchange: {}", event.getOrderId(), RabbitMQConfig.EXCHANGE_NAME);
        // Enviamos al fanout exchange (routing key vacía o cualquiera, el fanout la ignora)
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "", event);
    }
}
