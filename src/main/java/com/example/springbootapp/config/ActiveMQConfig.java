package com.example.springbootapp.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jms.autoconfigure.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig {

    public static final String CONNECTION_FACTORY_NAME = "connectionFactory1";
    public static final String JMS_TEMPLATE_NAME = "jmsTemplate1";
    public static final String LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME = "listenerContainerFactoryTopic1";
    public static final String LISTENER_CONTAINER_FACTORY_FOR_QUEUE1_NAME = "listenerContainerFactoryQueue1";
    public static final String LISTENER_CONTAINER_FACTORY_FOR_COMPANY_TOPIC_NAME = "listenerContainerFactoryCompanyTopic1";
    public static final String QUEUE_NAME = "queue1";
    public static final String TOPIC_NAME = "topic1";
    public static final String COMPANY_TOPIC_NAME = "company.topic1";

    @Value("${ACTIVE_MQ_URL}")
    private String brokerUrl;

    /**
     * Here we define url, login and password
     */
    @Bean(CONNECTION_FACTORY_NAME)
    ConnectionFactory connectionFactory1() {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    /**
     * Configure JmsTemplate per each connection if we have multiple
     */
    @Bean(JMS_TEMPLATE_NAME)
    JmsTemplate jmsTemplate1() {
        var jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory1());
//        jmsTemplate.setMessageConverter(); // set json or xml converter
        jmsTemplate.setReceiveTimeout(15_000); // 15s
        jmsTemplate.setExplicitQosEnabled(true);
        jmsTemplate.setTimeToLive(13_000); // 13s
//        jmsTemplate.setPubSubDomain(true); // send message to topic
//        jmsTemplate.setPubSubDomain(false); // send message to queue
        return jmsTemplate;
    }

    /**
     * Configure factory for each listener (method with @JmsListener)
     */
    @Bean(LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME)
    JmsListenerContainerFactory<?> listenerContainerFactory1(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        var factory = new DefaultJmsListenerContainerFactory();
        // This provides all auto-configured defaults to this factory, including the message converter
        configurer.configure(factory, connectionFactory1());
        // You could still override some settings if necessary.
        factory.setClientId("springbootapp." + TOPIC_NAME);
        factory.setPubSubDomain(Boolean.TRUE); // topic
        factory.setSubscriptionDurable(Boolean.FALSE); // don't save after broker restart
        return factory;
    }

    /**
     * Configure factory for each listener (method with @JmsListener)
     */
    @Bean(LISTENER_CONTAINER_FACTORY_FOR_QUEUE1_NAME)
    JmsListenerContainerFactory<?> listenerContainerFactory2(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        var factory = new DefaultJmsListenerContainerFactory();
        // This provides all auto-configured defaults to this factory, including the message converter
        configurer.configure(factory, connectionFactory1());
        // You could still override some settings if necessary.
        factory.setClientId("springbootapp." + QUEUE_NAME);
        factory.setPubSubDomain(Boolean.FALSE); // queue
        factory.setSubscriptionDurable(Boolean.FALSE); // don't save after broker restart
        return factory;
    }

    /**
     * Topic for receiving companies
     */
    @Bean(LISTENER_CONTAINER_FACTORY_FOR_COMPANY_TOPIC_NAME)
    JmsListenerContainerFactory<?> listenerContainerFactory3(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        var factory = new DefaultJmsListenerContainerFactory();
        // This provides all auto-configured defaults to this factory, including the message converter
        configurer.configure(factory, connectionFactory1());
        // You could still override some settings if necessary.
        factory.setClientId("springbootapp." + COMPANY_TOPIC_NAME);
        factory.setPubSubDomain(Boolean.TRUE); // topic
        factory.setSubscriptionDurable(Boolean.FALSE); // don't save after broker restart
        return factory;
    }
}
