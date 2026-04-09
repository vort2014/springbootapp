package com.example.springbootapp.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jms.autoconfigure.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
public class TestActiveMQConfig {

    public static final String TEST_LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME = "testListenerContainerFactoryTopic1";
    public static final String TEST_LISTENER_CONTAINER_FACTORY_FOR_COMPANY_TOPIC1_NAME = "testListenerContainerFactoryCompanyTopic1";

    /**
     * Configure factory for each listener (method with @JmsListener)
     */
    @Bean(TEST_LISTENER_CONTAINER_FACTORY_FOR_TOPIC1_NAME)
    JmsListenerContainerFactory<?> listenerContainerFactory1(DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                             @Qualifier(ActiveMQConfig.CONNECTION_FACTORY_NAME) ConnectionFactory connectionFactory) {
        var factory = new DefaultJmsListenerContainerFactory();
        // This provides all auto-configured defaults to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some settings if necessary.
        factory.setClientId("testClientForTopic." + ActiveMQConfig.TOPIC_NAME);
        factory.setPubSubDomain(Boolean.TRUE); // topic
        factory.setSubscriptionDurable(Boolean.FALSE); // don't save after broker restart
        return factory;
    }

    /**
     * Companies topic JmsListenerContainerFactory
     */
    @Bean(TEST_LISTENER_CONTAINER_FACTORY_FOR_COMPANY_TOPIC1_NAME)
    JmsListenerContainerFactory<?> listenerContainerFactory3(DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                             @Qualifier(ActiveMQConfig.CONNECTION_FACTORY_NAME) ConnectionFactory connectionFactory) {
        var factory = new DefaultJmsListenerContainerFactory();
        // This provides all auto-configured defaults to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some settings if necessary.
        factory.setClientId("testClientForTopic." + ActiveMQConfig.COMPANY_TOPIC_NAME);
        factory.setPubSubDomain(Boolean.TRUE); // topic
        factory.setSubscriptionDurable(Boolean.FALSE); // don't save after broker restart
        return factory;
    }
}
