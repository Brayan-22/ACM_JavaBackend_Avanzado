package com.acm.rabbitplayground.consumer;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(TestConsumer.class);
    @RabbitListener(queues = "test.queue")
    public void receiveMessage(String message) {
        logger.info("Received message: {}", message);
    }

}
