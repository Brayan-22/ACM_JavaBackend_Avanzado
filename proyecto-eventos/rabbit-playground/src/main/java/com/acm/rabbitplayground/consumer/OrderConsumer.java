package com.acm.rabbitplayground.consumer;

import com.acm.rabbitplayground.config.RabbitConfig;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(OrderConsumer.class);
    @RabbitListener(queues = RabbitConfig.QUEUE_A)
    public void onOrderEvent(String msg){
        log.info("[Cola A - Order*]{}", msg);
        // Cola de inventario
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "inventory.update", "Actualizacion de stock para: " + msg);
    }
}
