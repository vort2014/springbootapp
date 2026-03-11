package com.example.springbootapp.redis;

import com.example.springbootapp.config.RedisStreamConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisStreamProducer {

    private final StringRedisTemplate template;

    /**
     * Send message to Redis streams
     */
    @Scheduled(initialDelay = 1, timeUnit = TimeUnit.SECONDS)
    void sendToStream() {
        var event = Map.of(
                "someKey1", "someValue1",
                "someKey2", "someValue2",
                "someKey3", "someValue3");
        try {
            RecordId recordId = template.opsForStream().add(RedisStreamConfig.STREAM_KEY, event);
            if (recordId != null) log.info("Send event {} to stream '{}' with recordId '{}'", event, RedisStreamConfig.STREAM_KEY, recordId);
            else log.info("Failed to send event {} to stream: {}, received null recordId", event, RedisStreamConfig.STREAM_KEY);
        } catch (Exception e) {
            log.error("Error publishing data", e);
        }
    }
}
