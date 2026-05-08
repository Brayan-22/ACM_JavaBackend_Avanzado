package com.acm.shippingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "ecommerce";
    public static final String PAYMENT_SUCCESS_QUEUE = "q.shipping.payment-success";
    public static final String PAYMENT_SUCCESS_RK = "payment.success";
    public static final String SHIPMENT_SCHEDULED_RK = "shipping.scheduled";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue paymentSuccessQueue() {
        return QueueBuilder.durable(PAYMENT_SUCCESS_QUEUE).build();
    }

    @Bean
    public Binding bindPaymentSuccess(Queue paymentSuccessQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentSuccessQueue).to(exchange).with(PAYMENT_SUCCESS_RK);
    }

    @Bean
    public MessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }
}