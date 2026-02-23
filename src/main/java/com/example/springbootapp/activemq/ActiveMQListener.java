package com.example.springbootapp.activemq;

import com.example.springbootapp.config.ActiveMQConfig;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActiveMQListener {

    @JmsListener(destination = ActiveMQConfig.TOPIC_NAME, containerFactory = ActiveMQConfig.LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME)
    void onTopicMessage(Message message) throws JMSException {
        var destination = message.getJMSDestination();
        var correlationId = message.getJMSCorrelationID();
        var text = ((TextMessage)  message).getText();
        log.info("Received message '{}' on:{}, jmsCorrelationID:{}", text, destination, correlationId);
    }

    @JmsListener(destination = ActiveMQConfig.QUEUE_NAME, containerFactory = ActiveMQConfig.LISTENER_CONTAINER_FACTORY_FOR_QUEUE1_NAME)
    void onQueueMessage(Message message) throws JMSException {
        var destination = message.getJMSDestination();
        var correlationId = message.getJMSCorrelationID();
        var text = ((TextMessage)  message).getText();
        log.info("Received message '{}' on:{}, jmsCorrelationID:{}", text, destination, correlationId);
    }
}
