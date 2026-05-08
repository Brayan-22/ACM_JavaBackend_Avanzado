package com.acm.rabbitplayground.consumer;

import com.acm.rabbitplayground.config.RabbitConfig;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(InventoryConsumer.class);
    @RabbitListener(queues = RabbitConfig.QUEUE_B)
    public void onInventoryEvent(String msg){
        log.info("[Cola B - Inventory#] {}", msg);
    }
}
