package com.acm.orderservice.consumer;

import com.acm.orderservice.model.OrderStatus;
import com.acm.orderservice.persistence.repository.OrderJpaRepository;
import com.acm.orderservice.shared.ShipmentScheduledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderFinalizedConsumer {

    private final OrderJpaRepository repository;

    @RabbitListener(queues = "q.order.finalized")
    public void onSuccessShippingEvent(ShipmentScheduledEvent event){
        repository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.SHIPPED);
            repository.save(order);
            log.info("Pedido {} finalizado exitosamente", event.getOrderId());
        });
    }

}
