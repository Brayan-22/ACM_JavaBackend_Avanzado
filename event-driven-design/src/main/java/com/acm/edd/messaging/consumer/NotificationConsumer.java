package com.acm.edd.messaging.consumer;

import com.acm.edd.config.RabbitMQConfig;
import com.acm.edd.messaging.event.OrderCreatedEvent;
import com.acm.edd.model.ProcessedEvent;
import com.acm.edd.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final ProcessedEventRepository processedEventRepository;

    private static final String CONSUMER_NAME = "NOTIFICATION_CONSUMER";

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICATION)
    @Transactional
    public void consumeOrderCreated(OrderCreatedEvent event) throws InterruptedException {
        log.info("==> [{}] Recibe evento de OrderId: {} (EventId: {})", CONSUMER_NAME, event.getOrderId(), event.getEventId());

        if (processedEventRepository.existsByEventIdAndConsumerName(event.getEventId(), CONSUMER_NAME)) {
            log.info("[{}] ♻️ Evento {} ya procesado. Ignorando silenciosamente.", CONSUMER_NAME, event.getEventId());
            return;
        }

        // Simular tiempo de envío asíncrono superior al de la respuesta en REST
        Thread.sleep(1500); // 1.5 seg
        log.info("[{}] 📧 Email / Push Notification enviado al cliente: {} para la Orden: {}", 
                 CONSUMER_NAME, event.getCustomerId(), event.getOrderId());

        ProcessedEvent processedEvent = new ProcessedEvent(event.getEventId(), CONSUMER_NAME, LocalDateTime.now());
        processedEventRepository.save(processedEvent);

        log.info("==> [{}] Procesamiento exitoso para OrderId: {}", CONSUMER_NAME, event.getOrderId());
    }
}
