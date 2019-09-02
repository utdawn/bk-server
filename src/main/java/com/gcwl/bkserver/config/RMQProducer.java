package com.gcwl.bkserver.config;

import com.gcwl.bkserver.entity.Orders;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RMQProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Orders orders) {
//        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("orderQueue", orders);
    }
}
