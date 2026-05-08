package com.acm.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "ecommerce";
    public static final String STOCK_RESERVED_QUEUE = "q.payment.stock-reserved";
    public static final String STOCK_RESERVED_RK = "inventory.reserved";
    public static final String PAYMENT_SUCCESS_RK = "payment.success";
    public static final String PAYMENT_FAILED_RK = "payment.failed";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue stockReservedQueue() {
        return QueueBuilder.durable(STOCK_RESERVED_QUEUE).build();
    }

    @Bean
    public Binding bindStockReserved(Queue stockReservedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(stockReservedQueue).to(exchange).with(STOCK_RESERVED_RK);
    }

    @Bean
    public MessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }

}