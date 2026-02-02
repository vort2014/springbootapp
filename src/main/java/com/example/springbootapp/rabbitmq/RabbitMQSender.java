package com.example.springbootapp.rabbitmq;

import com.example.springbootapp.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Message is send directly to the queue.
     * Then a receiver listens to this queue and receives this message.
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void sendToQueue() {
        var message = UUID.randomUUID().toString();
//        var message = CompanyResponseJson.builder().id(UUID.randomUUID().toString()).name("companyName").build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME1, message);
        log.info("Message sent '{}' to queue={}", message, RabbitMQConfig.QUEUE_NAME1);
    }

    /**
     * Message is sent to exchange with type topic to which queue is bound
     * with routing key 'foo.bar.#'.
     *
     * Then a receiver listens to this queue and receives this message with routing key "foo.bar.baz"
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void sendToTopicExchange() {
        var message = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "foo.bar.baz", message);
        log.info("Message sent '{}' to topicExchange={}", message, RabbitMQConfig.TOPIC_EXCHANGE_NAME);
    }

    /**
     * Messages will be sent to all bind queues despite routing key value
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void sendToFanoutExchange() {
        var message = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_NAME, "qq.ww.ww", message);
        log.info("Message sent '{}' to fanoutExchange={}", message, RabbitMQConfig.FANOUT_EXCHANGE_NAME);
    }

    /**
     * Messages will be sent if routing key exactly match
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void sendToDirectExchange() {
        var message = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, "orange", message);
        log.info("Message sent '{}' to directExchange={}", message, RabbitMQConfig.DIRECT_EXCHANGE_NAME);
    }
}
