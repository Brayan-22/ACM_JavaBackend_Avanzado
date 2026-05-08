package com.acm.rabbitplayground;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitPlaygroundApplication.class, args);
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test.queue", false);
    }
}
