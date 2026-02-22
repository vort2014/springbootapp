package com.example.springbootapp.rabbitmq;

import com.example.springbootapp.config.RabbitMQConfig;
import com.example.springbootapp.web.controller.company.CompanyResponseJson;
import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Streams were introduced in version 3.9
 * Streams differ from queues that it's append only log of messages that can be read
 * multiple times by clients from any point until they expire.
 * Client can start reading from different offset.
 * You need to enable stream plugin on RabbitMQ message broker.
 * <p>
 * https://docs.spring.io/spring-amqp/reference/stream.html
 * Use cases:
 * https://www.rabbitmq.com/docs/streams#use-cases
 * TODO:
 * 2. What is super stream?
 * 3. Make Docker container in IT tests the same as in compose.yml
 */
@Profile("!test && !it")
@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQStreamListener {

    /**
     * Basic stream listener
     */
    @RabbitListener(queues = {RabbitMQConfig.STREAM_NAME1})
    public void receiveStream1(CompanyResponseJson message) {
        log.info("Received message '{}' from stream={}", message, RabbitMQConfig.STREAM_NAME1);
    }

    /**
     * The configuration of this listener isn't in the annotation parameters but in the containerFactory configuration.
     * This allows to consume starting from predefined position (in the containerFactory configuration)
     */
    @RabbitListener(queues = RabbitMQConfig.STREAM_NAME3, containerFactory = RabbitMQConfig.STREAM_NAME3_LISTENER_FACTORY_NAME)
    void receiveStream3(Message message, MessageHandler.Context context) {
        long offset = context.offset();
        String body = new String(message.getBodyAsBinary(), StandardCharsets.UTF_8);
        log.info("offset {} message '{}' from stream={}", offset, body, RabbitMQConfig.STREAM_NAME3);
    }
}
