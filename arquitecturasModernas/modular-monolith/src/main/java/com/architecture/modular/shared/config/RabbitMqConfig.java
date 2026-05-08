package com.architecture.modular.shared.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.messaging.type", havingValue = "rabbitmq")
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "modular.exchange";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange modularExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Colas requeridas
    @Bean
    public Queue ordenCreadaQueue() {
        return new Queue("inventario.ordenCreada.queue");
    }

    @Bean
    public Queue ordenValidadaQueue() {
        return new Queue("orden.ordenValidada.queue");
    }

    @Bean
    public Queue ordenRechazadaQueue() {
        return new Queue("orden.ordenRechazada.queue");
    }

    // Bindings
    @Bean
    public Binding bindingOrdenCreada(Queue ordenCreadaQueue, TopicExchange modularExchange) {
        return BindingBuilder.bind(ordenCreadaQueue).to(modularExchange).with("OrdenCreadaEvent");
    }

    @Bean
    public Binding bindingOrdenValidada(Queue ordenValidadaQueue, TopicExchange modularExchange) {
        return BindingBuilder.bind(ordenValidadaQueue).to(modularExchange).with("OrdenValidadaEvent");
    }

    @Bean
    public Binding bindingOrdenRechazada(Queue ordenRechazadaQueue, TopicExchange modularExchange) {
        return BindingBuilder.bind(ordenRechazadaQueue).to(modularExchange).with("OrdenRechazadaEvent");
    }
}
