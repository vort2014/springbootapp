package com.example.springbootapp.config;

import com.example.springbootapp.rabbitmq.RabbitMQReceiver;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * https://docs.spring.io/spring-boot/reference/messaging/amqp.html
 * https://spring.io/blog/2010/06/14/understanding-amqp-the-protocol-used-by-rabbitmq
 * https://docs.spring.io/spring-amqp/reference/index.html
 * https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp
 */
@Configuration
@EnableScheduling
public class RabbitMQConfig {

    public static final String QUEUE_NAME1 = "QUEUE_NAME1";
    public static final String QUEUE_NAME2 = "QUEUE_NAME2";
    public static final String TOPIC_EXCHANGE_NAME = "TOPIC_EXCHANGE_NAME1";
    public static final String DIRECT_EXCHANGE_NAME = "DIRECT_EXCHANGE_NAME";
    public static final String FANOUT_EXCHANGE_NAME = "FANOUT_EXCHANGE_NAME1";
    private static final boolean DURABLE = false; // we don't need messages after restart

    // If we send json object in AMQP messages we need to serialize/deserialize them to json
    @Bean
    MessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    Queue queue1() {
        return new Queue(QUEUE_NAME1, DURABLE);
    }
    @Bean
    Queue queue2() {
        return new Queue(QUEUE_NAME2, DURABLE);
    }
    // This queue will be eliminated once consumer disconnects
//    @Bean
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }




    /**
     * Send messages to queues based on routing key wildcard
     */
    @Bean
    TopicExchange topicExchange() {
        // autoDelete=true if the server should delete the exchange when it is no longer in use
        return new TopicExchange(TOPIC_EXCHANGE_NAME, DURABLE, true);
    }
    /**
     * Here we bind queue to exchange with type topic and routing key 'foo.bar.#'
     */
    @Bean
    Binding topicExchangeBinding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("foo.bar.#");
    }



    /**
     * Send messages to queues based on routing key, no wildcards allowed
     * the routing key should be exactly the same
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, DURABLE, true);
    }
    @Bean
    Binding directExchangeBinding1() {
        return BindingBuilder.bind(queue1()).to(directExchange()).with("orange");
    }






    /**
     * sends messages to all queues which are bound despite routing key
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, DURABLE, true);
    }
    // bind queue1 to fanout
    @Bean
    Binding fanoutExchangeBinding1() {
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }
    // bind queue2 to fanout
    @Bean
    Binding fanoutExchangeBinding2() {
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }





    @Bean
    RabbitMQReceiver receiver1() {
        return new RabbitMQReceiver("receiver1");
    }
    @Bean
    RabbitMQReceiver receiver2() {
        return new RabbitMQReceiver("receiver2");
    }
}
