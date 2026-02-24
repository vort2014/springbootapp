package com.example.springbootapp.activemq;

import com.example.springbootapp.config.ActiveMQConfig;
import com.example.springbootapp.dao.company.CompanyEntity;
import com.example.springbootapp.dao.company.CompanyRepository;
import com.example.springbootapp.service.lastupdate.LastUpdateService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActiveMQListener {

    private final LastUpdateService lastUpdateService;
    private final CompanyRepository companyRepository;

    /**
     * Use topics as much as possible
     */
    @JmsListener(destination = ActiveMQConfig.TOPIC_NAME, containerFactory = ActiveMQConfig.LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME)
    void onMessage1(Message message) throws JMSException {
        var destination = message.getJMSDestination();
        var correlationId = message.getJMSCorrelationID();
        var text = ((TextMessage)  message).getText();
        log.info("Received message '{}' on:{}, jmsCorrelationID:{}", text, destination, correlationId);
    }

    /**
     * Queues are bad, they can't be tested as this method may snatch messages from test methods
     */
    @JmsListener(destination = ActiveMQConfig.QUEUE_NAME, containerFactory = ActiveMQConfig.LISTENER_CONTAINER_FACTORY_FOR_QUEUE1_NAME)
    void onMessage2(Message message) throws JMSException {
        var destination = message.getJMSDestination();
        var correlationId = message.getJMSCorrelationID();
        var text = ((TextMessage)  message).getText();
        log.info("Received message '{}' on:{}, jmsCorrelationID:{}", text, destination, correlationId);
    }

    @Transactional
    @JmsListener(destination = ActiveMQConfig.COMPANY_TOPIC_NAME, containerFactory = ActiveMQConfig.LISTENER_CONTAINER_FACTORY_FOR_COMPANY_TOPIC_NAME)
    void onMessage3(Message message) throws JMSException {
        var companyName = ((TextMessage)  message).getText();
        var companyEntity = CompanyEntity.builder()
                .name(companyName)
                .build();
        companyRepository.save(companyEntity);
        lastUpdateService.updateDatabaseTimestamp(Instant.now());
    }
}
