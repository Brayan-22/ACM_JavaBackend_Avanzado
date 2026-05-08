package com.acm.paymentservice.service;


import com.acm.paymentservice.config.RabbitConfig;
import com.acm.paymentservice.persistence.entity.Payment;
import com.acm.paymentservice.persistence.entity.PaymentStatus;
import com.acm.paymentservice.persistence.repository.PaymentJpaRepository;
import com.acm.paymentservice.shared.PaymentFailedEvent;
import com.acm.paymentservice.shared.PaymentProcessedEvent;
import com.acm.paymentservice.shared.StockReservedEvent;
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
public class PaymentService {

    private final PaymentJpaRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void processPayment(StockReservedEvent event) {
        String orderId = event.getOrderId();
        // Idempotencia: si ya existe un pago para este pedido, ignoramos
        if (paymentRepository.existsByOrderId(orderId)) {
            log.warn("Pago ya procesado para orderId: {}, ignorando.", orderId);
            return;
        }

        // Crear pago en estado PENDING
        Payment payment = Payment.builder()
                .orderId(orderId)
                .status(PaymentStatus.PENDING)
                .amount(100.0)  // Monto fijo simulado
                .createdAt(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);

        // Simular resultado (50% exito)
        if (Math.random() < 0.5) {
            String transactionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setTransactionId(transactionId);
            paymentRepository.save(payment);

            PaymentProcessedEvent processedEvent = PaymentProcessedEvent.builder()
                    .orderId(orderId)
                    .transactionId(transactionId)
                    .amount(payment.getAmount())
                    .timestamp(LocalDateTime.now())
                    .build();
            log.info("Pago exitoso para orderId: {}, txId: {}", orderId, transactionId);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.PAYMENT_SUCCESS_RK, processedEvent);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

            PaymentFailedEvent failedEvent = PaymentFailedEvent.builder()
                    .orderId(orderId)
                    .trace("Simulated payment failure")
                    .timestamp(LocalDateTime.now())
                    .build();
            log.info("Pago fallido para orderId: {}", orderId);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.PAYMENT_FAILED_RK, failedEvent);
        }
    }
}
