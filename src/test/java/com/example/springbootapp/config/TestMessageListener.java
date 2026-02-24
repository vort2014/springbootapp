package com.example.springbootapp.config;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * This class helps to test that Message Broker received expected message
 */
@Service
@Slf4j
public class TestMessageListener {

    // queues can't be tested by such approach because nontest methods may snatch messages
//    private LinkedBlockingDeque<Object> queue1 = new LinkedBlockingDeque<>();
    private final LinkedBlockingDeque<Object> topic1 = new LinkedBlockingDeque<>();
    private final LinkedBlockingDeque<Object> companyTopic1 = new LinkedBlockingDeque<>();

    @JmsListener(destination = ActiveMQConfig.TOPIC_NAME, containerFactory = TestActiveMQConfig.TEST_LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME)
    void onMessage1(Message message) throws JMSException {
        var text = ((TextMessage)  message).getText();
        topic1.addLast(text);
    }

    @JmsListener(destination = ActiveMQConfig.COMPANY_TOPIC_NAME, containerFactory = TestActiveMQConfig.TEST_LISTENER_CONTAINER_FACTORY_FOR_COMPANY_TOPIC1_NAME)
    void onMessage2(Message message) throws JMSException {
        var text = ((TextMessage)  message).getText();
        companyTopic1.addLast(text);
    }

    @SneakyThrows
    public Object getFirstFromTopic1() {
        return topic1.pollFirst(10, TimeUnit.SECONDS);
    }

    @SneakyThrows
    public Object getFirstFromCompanyTopic1() {
        return companyTopic1.pollFirst(10, TimeUnit.SECONDS);
    }

    public void clear() {
        topic1.clear();
        companyTopic1.clear();
    }
}
