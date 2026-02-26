package com.example.springbootapp.postgresql;

import com.example.springbootapp.config.PostgreSQLMessageBrokerConfig;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostgreSQLListener {

    @Resource(name = PostgreSQLMessageBrokerConfig.SPECIAL_JDBC_TEMPLATE)
    private JdbcTemplate jdbcTemplate;
    private volatile boolean isStopped = true;

    /**
     * Subscription uses the separate connection to the database which holds forever
     * We subscribe to a channel from a jdbc connection and reuse the same connection
     * to receive notifications.
     */
    @Scheduled(initialDelay = 0)
    void subscribe() {
        Runnable runnable = () -> {
            jdbcTemplate.execute((Connection c) -> {
                isStopped = false;
                try {
                    c.createStatement().execute("LISTEN " + PostgreSQLMessageBrokerConfig.ORDERS_CHANNEL);
                    PGConnection pgconn = c.unwrap(PGConnection.class);
                    while (!Thread.currentThread().isInterrupted()) {
                        PGNotification[] notifications = pgconn.getNotifications(0); // 0 means wait for notifications forever
                        if (notifications == null || notifications.length == 0) continue;
                        for (PGNotification notification : notifications) {
                            String channel = notification.getName();
                            String message = notification.getParameter();
                            log.info("Received message '{}' on channel {}", message, channel);
                        }
                    }
                } catch (Exception e) {
                    log.error("Subscription is corrupted", e);
                }
                isStopped = true;
                return 0;
            });
        };

        // is case of network or database issues we need to resubscribe
        // start in virtual thread to make faster JVM shutdown
        Thread.ofVirtual().start(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (isStopped) {
                    log.info("Subscribing to {} ...", PostgreSQLMessageBrokerConfig.ORDERS_CHANNEL);
                    Thread.ofVirtual().start(runnable);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
