package com.acm.rabbitplayground.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "test.topic";
    public static final String QUEUE_A = "queue.a";
    public static final String QUEUE_B = "queue.b";

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queueA(){
        return new Queue(QUEUE_A, false);
    }

    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B, false);
    }

    @Bean
    public Binding bindingA(Queue queueA, TopicExchange exchange){
        return BindingBuilder.bind(queueA).to(exchange).with("order.*");
    }

    @Bean
    public Binding bindingB(Queue queueB, TopicExchange exchange){
        return BindingBuilder.bind(queueB).to(exchange).with("inventory.#");
    }
}
