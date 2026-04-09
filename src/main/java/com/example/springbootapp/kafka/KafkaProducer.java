package com.example.springbootapp.kafka;

import com.example.springbootapp.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(initialDelay = 0)
    void sendMessage() {
        for (int i = 0; i < 10; i++) {
            var message = UUID.randomUUID().toString();

            // whenComplete is not necessary
            kafkaTemplate.send(KafkaConfig.TOPIC_NAME, message).whenComplete((result, ex) -> {
                if (ex == null) log.info("Send message '{}' with offset: {} to topic: {}", message, result.getRecordMetadata().offset(), KafkaConfig.TOPIC_NAME);
                else log.error("Unable to send message '{}' due to: {}", message, ex.getMessage());
            });
        }
    }
}
