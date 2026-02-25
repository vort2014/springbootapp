package com.example.springbootapp.postgresql;

import com.example.springbootapp.config.PostgreSQLMessageBrokerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostgreSQLProducer {

    private final JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    @Transactional
    void sendToOrdersChannel() {
        var message = UUID.randomUUID().toString();
        jdbcTemplate.execute("NOTIFY " + PostgreSQLMessageBrokerConfig.ORDERS_CHANNEL + ", '" + message + "'");
        log.info("Message is sent '{}' to channel {}", message, PostgreSQLMessageBrokerConfig.ORDERS_CHANNEL);
    }
}
