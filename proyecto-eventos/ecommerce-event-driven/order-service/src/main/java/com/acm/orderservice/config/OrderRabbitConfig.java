package com.acm.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitConfig {
    public static final String EXCHANGE = "ecommerce";
    public static final String ORDER_PLACED_RK = "order.placed";

    // Colas de compensación
    public static final String OUT_OF_STOCK_QUEUE = "q.order.out-of-stock";
    public static final String PAYMENT_FAILED_QUEUE = "q.order.payment-failed";
    public static final String SHIPPING_SUCCESS_QUEUE = "q.order.finalized";
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue outOfStockQueue() {
        return QueueBuilder.durable(OUT_OF_STOCK_QUEUE).build();
    }

    @Bean
    public Queue paymentFailedQueue() {
        return QueueBuilder.durable(PAYMENT_FAILED_QUEUE).build();
    }

    @Bean
    public Queue shippingFinalizedQueue(){
        return QueueBuilder.durable(SHIPPING_SUCCESS_QUEUE).build();
    }

    @Bean
    public Binding bindShippingFinalized(Queue shippingFinalizedQueue, TopicExchange exchange){
        return BindingBuilder.bind(shippingFinalizedQueue).to(exchange).with("shipping.scheduled");
    }

    @Bean
    public Binding bindOutOfStock(Queue outOfStockQueue, TopicExchange exchange) {
        return BindingBuilder.bind(outOfStockQueue).to(exchange).with("inventory.out");
    }

    @Bean
    public Binding bindPaymentFailed(Queue paymentFailedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentFailedQueue).to(exchange).with("payment.failed");
    }

    @Bean
    public MessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }
}
