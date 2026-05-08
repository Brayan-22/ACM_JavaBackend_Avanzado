package com.architecture.modular.inventario.adapter.in.event;

import com.architecture.modular.inventario.InventarioService;
import com.architecture.modular.shared.OrdenCreadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "spring")
@RequiredArgsConstructor
public class SpringOrdenCreadaListener {

    private final InventarioService inventarioService;

    @EventListener
    public void onOrdenCreada(OrdenCreadaEvent event) {
        inventarioService.procesarOrdenCreada(event);
    }
}
