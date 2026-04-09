package com.example.springbootapp.kafka;

import com.example.springbootapp.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListener1 {

    @KafkaListener(topics = KafkaConfig.TOPIC_NAME, groupId = KafkaConfig.GROUP_ID)
    void onMessage(@Payload String message,
                   @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                   @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message '{}' with offset: {} on topic:{}, partition: {}", message, offset, KafkaConfig.TOPIC_NAME, partition);
    }
}
