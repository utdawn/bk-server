package com.gcwl.bkserver.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue"); //配置一个名称为"orderQueue"的Queue队列
    }
}
