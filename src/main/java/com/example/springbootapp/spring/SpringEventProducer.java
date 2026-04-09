package com.example.springbootapp.spiring;

import com.example.springbootapp.web.controller.company.CompanyResponseJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringEventProducer {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(initialDelay = 0)
    void sendMessage() {
        for (int i = 0; i < 1000; i++) {
            var message = CompanyResponseJson.builder()
                    .id(UUID.randomUUID().toString())
                    .build();
            applicationEventPublisher.publishEvent(message);
            log.info("Sent message: {}", message);
        }
    }
}
