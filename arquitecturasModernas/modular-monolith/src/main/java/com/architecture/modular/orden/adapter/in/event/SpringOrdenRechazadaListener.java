package com.architecture.modular.orden.adapter.in.event;

import com.architecture.modular.orden.OrdenService;
import com.architecture.modular.shared.OrdenRechazadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "spring")
@RequiredArgsConstructor
public class SpringOrdenRechazadaListener {

    private final OrdenService ordenService;

    @EventListener
    public void onOrdenRechazada(OrdenRechazadaEvent event) {
        ordenService.marcarComoRechazada(event.ordenId(), event.motivo());
    }
}
