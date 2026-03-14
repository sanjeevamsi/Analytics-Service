package com.tracker.Analytics.consumer;

import com.rabbitmq.client.Channel;
import com.tracker.Analytics.dto.BudgetExceedEvent;
import com.tracker.Analytics.service.NotificationService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @Autowired
    public NotificationService notificationService;

    //message to sent the queue now the consume has to pick
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessageServer(BudgetExceedEvent event, Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(deliveryTag);
        try {
            notificationService.processBudgetAlert(event);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }

        Thread.sleep(1000);
        System.out.println("Message is received from mq broker : " + event);
    }
}
