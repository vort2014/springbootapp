package com.example.springbootapp.rabbitmq;

import com.example.springbootapp.config.RabbitMQConfig;
import com.example.springbootapp.web.controller.company.CompanyResponseJson;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * We need to create two bean in configuration because RabbitMQ forbids
 * creating two @RabbitHandler methods with the same signature and queue name.
 */
@Slf4j
@RequiredArgsConstructor
public class RabbitMQReceiver {

    private final String receiverName;

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME1})
    public void receive(String message) {
        log.info("Received message '{}' on receiver={}, queue={}", message, receiverName, RabbitMQConfig.QUEUE_NAME1);
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME2})
    public void receiveQueue2(String message) {
        log.info("Received message '{}' on receiver={}, queue={}", message, receiverName, RabbitMQConfig.QUEUE_NAME2);
    }

    /**
     * For json messages
     * @param message
     */
//    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME})
    public void receive(CompanyResponseJson message) {
        log.info("Received message '{}' on {}", message, receiverName);
    }

    /**
     * This property should be set in application.yml:
     *
     * spring.rabbitmq.listener.simple.acknowledge-mode: manual
     */
    //
//    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME})
    public void receiveWithAck(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            channel.basicAck(deliveryTag, false);
            log.info("Received message and acknowledge it '{}' on {}", message, receiverName);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
        }
    }
}
