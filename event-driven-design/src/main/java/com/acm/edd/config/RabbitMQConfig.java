package com.acm.edd.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "orders.exchange";
    public static final String QUEUE_INVENTORY = "inventory.orders.queue";
    public static final String QUEUE_NOTIFICATION = "notification.orders.queue";

    // Un Fanout Exchange envía el mensaje a TODAS las colas unidas (bindings) a él,
    // ignorando las routing keys.
    // Esto es ideal para un patrón Pub/Sub
    @Bean
    public FanoutExchange ordersExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    // Cola para el consumidor del inventario
    @Bean
    public Queue inventoryQueue() {
        return new Queue(QUEUE_INVENTORY, true);
    }

    // Cola para el consumidor de notificaciones
    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATION, true);
    }

    // Unimos la cola de inventario al Exchange
    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, FanoutExchange ordersExchange) {
        return BindingBuilder.bind(inventoryQueue).to(ordersExchange);
    }

    // Unimos la cola de notificaciones al Exchange
    @Bean
    public Binding notificationBinding(Queue notificationQueue, FanoutExchange ordersExchange) {
        return BindingBuilder.bind(notificationQueue).to(ordersExchange);
    }

    // Conversor de mensajes a JSON para que viajen como objetos legibles.
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
