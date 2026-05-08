package com.acm.edd.messaging.consumer;

import com.acm.edd.config.RabbitMQConfig;
import com.acm.edd.messaging.event.OrderCreatedEvent;
import com.acm.edd.model.Order;
import com.acm.edd.model.ProcessedEvent;
import com.acm.edd.repository.OrderRepository;
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
public class InventoryConsumer {

    private final ProcessedEventRepository processedEventRepository;
    private final OrderRepository orderRepository;

    private static final String CONSUMER_NAME = "INVENTORY_CONSUMER";

    public static boolean simulateFailure = false;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_INVENTORY)
    @Transactional
    public void consumeOrderCreated(OrderCreatedEvent event) throws InterruptedException {
        log.info("==> [{}] Recibe evento de OrderId: {} (EventId: {})", CONSUMER_NAME, event.getOrderId(),
                event.getEventId());

        // 1. Simulación Didáctica de Fallo
        if (simulateFailure) {
            log.warn("[{}] ¡FALLO SIMULADO ACTIVADO! Lanzando excepción para forzar Retry...", CONSUMER_NAME);
            throw new RuntimeException("Fallo simulado en el inventario.");
        }

        // 2. Control Didáctico de Idempotencia
        if (processedEventRepository.existsByEventIdAndConsumerName(event.getEventId(), CONSUMER_NAME)) {
            log.info("[{}] ♻️ Evento {} ya fue procesado. Ignorando silenciosamente (IDEMPOTENCIA activa).",
                    CONSUMER_NAME, event.getEventId());
            return; // Se descarta y no se vuelve a encolar, ya cumplió
        }

        // 3. Simular tiempo de carga asíncrono
        Thread.sleep(1000); // 1 segundo para evidenciar la asincronía y la consistencia eventual
        log.info("[{}] Simulación de descuento de inventario exitosa para OrderId: {}", CONSUMER_NAME,
                event.getOrderId());

        // 4. Actualizar estado de la orden (Consistencia Eventual)
        Order order = orderRepository.findById(event.getOrderId()).orElse(null);
        if (order != null) {
            order.setStatus("INVENTORY_RESERVED");
            orderRepository.save(order);
            log.info("[{}] Estado de OrderId: {} actualizado a INVENTORY_RESERVED", CONSUMER_NAME, event.getOrderId());
        }

        // 5. Guardar evento procesado para garantizar idempotencia en futuros
        // re-entregos
        ProcessedEvent processedEvent = new ProcessedEvent(event.getEventId(), CONSUMER_NAME, LocalDateTime.now());
        processedEventRepository.save(processedEvent);

        log.info("==> [{}] Procesamiento exitoso para OrderId: {}", CONSUMER_NAME, event.getOrderId());
    }
}
