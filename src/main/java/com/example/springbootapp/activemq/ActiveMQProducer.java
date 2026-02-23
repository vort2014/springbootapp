package com.example.springbootapp.activemq;

import com.example.springbootapp.config.ActiveMQConfig;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActiveMQProducer {

    private enum MessageType {
        Request,
        Response,
        Result,
        Command,
    }

    @Qualifier(ActiveMQConfig.JMS_TEMPLATE_NAME)
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void sendToTopic() {
        var destinationName = ActiveMQConfig.TOPIC_NAME;
        var topic = new ActiveMQTopic(destinationName); // this is a key
        var payload = UUID.randomUUID().toString();
        MessagePostProcessor messagePostProcessor = message -> {
            var jmsCorrelationId = UUID.randomUUID().toString();
            message.setJMSCorrelationID(jmsCorrelationId);
            message.setJMSType(MessageType.Command.toString());
            message.setStringProperty("MessageSender", "springbootapp");
            message.setStringProperty("DeliveryMode", "Persistent");
            log.info("Sent to topic:{} -> payload: {}", destinationName, ((TextMessage)message).getText());
            return message;
        };
        jmsTemplate.convertAndSend(topic, payload, messagePostProcessor);
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void sendToQueue() {
        var destinationName = ActiveMQConfig.QUEUE_NAME;
        var queue = new ActiveMQQueue(destinationName); // this is a key
        var payload = UUID.randomUUID().toString();
        MessagePostProcessor messagePostProcessor = message -> {
            var jmsCorrelationId = UUID.randomUUID().toString();
            message.setJMSCorrelationID(jmsCorrelationId);
            message.setJMSType(MessageType.Command.toString());
            message.setStringProperty("MessageSender", "springbootapp");
            message.setStringProperty("DeliveryMode", "Persistent");
            log.info("Sent to queue:{} -> payload: {}", destinationName, ((TextMessage)message).getText());
            return message;
        };
        jmsTemplate.convertAndSend(queue, payload, messagePostProcessor);
    }
}
