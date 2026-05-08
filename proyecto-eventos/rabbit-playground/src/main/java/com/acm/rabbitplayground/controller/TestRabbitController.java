package com.acm.rabbitplayground.controller;

import com.acm.rabbitplayground.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRabbitController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostMapping("/send")
    public String send(@RequestParam String msg){
        rabbitTemplate.convertAndSend("test.queue", msg);
        return "Message sent: " + msg;
    }

    @PostMapping("/order")
    public String placeHolder(@RequestParam String id){
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "order.placed", "Pedido "+id);
        return "Order placed: " + id;
    }
}
