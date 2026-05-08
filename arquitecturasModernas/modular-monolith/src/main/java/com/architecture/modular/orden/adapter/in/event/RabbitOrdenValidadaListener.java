package com.architecture.modular.orden.adapter.in.event;

import com.architecture.modular.orden.OrdenService;
import com.architecture.modular.shared.OrdenValidadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "rabbitmq")
@RequiredArgsConstructor
public class RabbitOrdenValidadaListener {

    private final OrdenService ordenService;

    @RabbitListener(queues = "orden.ordenValidada.queue")
    public void onOrdenValidada(OrdenValidadaEvent event) {
        ordenService.marcarComoValidada(event.ordenId());
    }
}
