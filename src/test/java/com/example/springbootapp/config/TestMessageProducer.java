package com.example.springbootapp.config;

import com.example.springbootapp.service.lastupdate.LastUpdateService;
import jakarta.jms.Destination;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * This class allows to send jms message and check for database changes before return
 * by means of LastUpdateService.
 * Then it is possible ASAP to assert database changes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TestMessageProducer {

    private final LastUpdateService lastUpdateService;
    @Autowired
    @Qualifier(ActiveMQConfig.JMS_TEMPLATE_NAME)
    private JmsTemplate jmsTemplate;

    public void sendFromTopic1(String payload, boolean waitForDatabaseChanges) {
        send(jmsTemplate, new ActiveMQTopic(ActiveMQConfig.TOPIC_NAME), payload, waitForDatabaseChanges);
    }

    public void sendFromCompanyTopic1(String payload, boolean waitForDatabaseChanges) {
        send(jmsTemplate, new ActiveMQTopic(ActiveMQConfig.COMPANY_TOPIC_NAME), payload, waitForDatabaseChanges);
    }

    private void send(JmsTemplate jmsTemplate, Destination destination, String payload, boolean waitForDatabaseChanges) {
        var previousDatabaseTimestamp = lastUpdateService.getLastUpdate().getDatabaseTimestamp();
        jmsTemplate.convertAndSend(destination, payload);
        if (waitForDatabaseChanges) waitForDatabaseChanges(previousDatabaseTimestamp);
    }

    @SneakyThrows
    private void waitForDatabaseChanges(Instant previousDatabaseTimestamp) {
        var maxWaitAttempts = 100; // 10 seconds
        var counter = 0;
        while (lastUpdateService.getLastUpdate().getDatabaseTimestamp().equals(previousDatabaseTimestamp)) {
            TimeUnit.MILLISECONDS.sleep(100);
            if (counter++ > maxWaitAttempts) throw new RuntimeException("Wait for database changes exceeds maximum attempts.");
        }
    }
}
