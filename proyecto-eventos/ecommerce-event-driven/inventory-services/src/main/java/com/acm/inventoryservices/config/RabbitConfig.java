package com.acm.inventoryservices.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "ecommerce";

    public static final String INVENTORY_ORDER_PLACED_QUEUE = "q.inventory.order-placed";
    public static final String INVENTORY_PAYMENT_FAILED_QUEUE = "q.inventory.payment-failed";

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue inventoryOrderPlacedQueue(){
        return new Queue(INVENTORY_ORDER_PLACED_QUEUE, true);
    }

    @Bean
    public Queue inventoryPaymentFailedQueue(){
        return new Queue(INVENTORY_PAYMENT_FAILED_QUEUE, true);
    }

    @Bean
    public Binding bindOrderPlaced(){
        return BindingBuilder.bind(inventoryOrderPlacedQueue()).to(exchange()).with("order.placed");
    }

    @Bean
    public Binding bindPaymentFailed(){
        return BindingBuilder.bind(inventoryPaymentFailedQueue()).to(exchange()).with("payment.failed");
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }
}
