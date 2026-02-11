package com.example.springbootapp.rabbitmq;

import com.example.springbootapp.config.RabbitMQConfig;
import com.example.springbootapp.web.controller.company.CompanyResponseJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Profile("!test && !it")
@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQStreamProducer {

    // each rabbitStreamTemplate is bound to single stream name in RabbitMQConfig
    private final RabbitStreamTemplate stream1;
    private final RabbitStreamTemplate stream3;

    /**
     * Send message to stream1
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void sendToStream1() {
        var message = CompanyResponseJson.builder().id(UUID.randomUUID().toString()).build();
        stream1.convertAndSend(message);
        log.info("Message is sent '{}' to stream={}", message, RabbitMQConfig.STREAM_NAME1);
    }

    /**
     * Send 100 messages to stream2
     */
    @Scheduled(initialDelay = 1)
    void sendToStream3() {
        for (int i = 0; i < 10; i++) {
            var message = UUID.randomUUID().toString();
            stream3.convertAndSend(message);
            log.info("Message {} is sent '{}' to stream={}", i, message, RabbitMQConfig.STREAM_NAME3);
        }
    }
}
