package com.acm.shippingservice.service;


import com.acm.shippingservice.config.RabbitConfig;
import com.acm.shippingservice.persistence.entity.Shipment;
import com.acm.shippingservice.persistence.entity.ShipmentStatus;
import com.acm.shippingservice.persistence.repository.ShipmentJpaRepository;
import com.acm.shippingservice.shared.PaymentProcessedEvent;
import com.acm.shippingservice.shared.ShipmentScheduledEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShipmentJpaRepository shipmentRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void scheduleShipment(PaymentProcessedEvent event) {
        String orderId = event.getOrderId();
        // Idempotencia: verificar si ya existe un envío para este pedido
        if (shipmentRepository.existsByOrderId(orderId)) {
            log.warn("Envío ya programado para orderId: {}, ignorando.", orderId);
            return;
        }
        // Generar tracking simulado
        String tracking = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Shipment shipment = Shipment.builder()
                .orderId(orderId)
                .trackingNumber(tracking)
                .status(ShipmentStatus.SCHEDULED)
                .scheduledAt(LocalDateTime.now())
                .build();
        shipmentRepository.save(shipment);
        // Publicar evento de envío programado
        ShipmentScheduledEvent scheduledEvent = ShipmentScheduledEvent.builder()
                .orderId(orderId)
                .trackingNumber(tracking)
                .timestamp(LocalDateTime.now())
                .build();
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.SHIPMENT_SCHEDULED_RK, scheduledEvent);
        log.info("Envío programado para orderId: {}, tracking: {}", orderId, tracking);
    }
}