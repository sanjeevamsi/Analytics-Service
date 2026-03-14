package com.tracker.Analytics.producer;

import com.tracker.Analytics.dto.BudgetExceedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate template;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingkey;

    public void sendNotification(BudgetExceedEvent event) {
        template.convertAndSend(exchange,routingkey,event);
        System.out.println("Sent event for user: " + event.userId());
    }
}
