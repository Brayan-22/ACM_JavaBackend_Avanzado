package com.architecture.modular.shared.adapter;

import com.architecture.modular.shared.port.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "rabbitmq")
@RequiredArgsConstructor
public class RabbitEventPublisherImpl implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object event) {
        // En un caso de producción aquí haríamos un ruteo o transformaríamos nombres de la clase
        // a tópicos. Por ahora, asumimos que el nombre de la clase es el binding routing key.
        String exchange = "modular.exchange";
        String routingKey = event.getClass().getSimpleName();
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
