package com.architecture.modular.inventario.adapter.in.event;

import com.architecture.modular.inventario.InventarioService;
import com.architecture.modular.shared.OrdenCreadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "rabbitmq")
@RequiredArgsConstructor
public class RabbitOrdenCreadaListener {

    private final InventarioService inventarioService;

    @RabbitListener(queues = "inventario.ordenCreada.queue")
    public void onOrdenCreada(OrdenCreadaEvent event) {
        inventarioService.procesarOrdenCreada(event);
    }
}
