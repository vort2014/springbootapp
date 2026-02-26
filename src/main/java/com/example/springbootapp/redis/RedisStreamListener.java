package com.example.springbootapp.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.stream.StreamListener;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    /**
     * Spring doesn't have any annotation for Redis, that's why we need to implement interface
     */
    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        log.info("Received message: '{}' from stream: '{}' with recordId: '{}'", message.getValue(), message.getStream(), message.getId());
    }
}
