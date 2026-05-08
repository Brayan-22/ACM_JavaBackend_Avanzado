package com.architecture.modular.orden.adapter.in.event;

import com.architecture.modular.orden.OrdenService;
import com.architecture.modular.shared.OrdenValidadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "spring")
@RequiredArgsConstructor
public class SpringOrdenValidadaListener {

    private final OrdenService ordenService;

    @EventListener
    public void onOrdenValidada(OrdenValidadaEvent event) {
        ordenService.marcarComoValidada(event.ordenId());
    }
}
