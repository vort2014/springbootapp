package com.example.springbootapp.config;

import com.example.springbootapp.redis.RedisStreamListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

/**
 * There are also Consumer groups - is a concept when multiple clients read from the same stream different messages
 * to horizontally scale message processing. It requires slightly different configuration
 *
 * https://docs.spring.io/spring-data/redis/reference/redis/redis-streams.html
 * https://medium.com/@jayanthpawar18/real-time-data-ingestion-and-delivery-using-spring-boot-and-redis-streams-a-beginner-friendly-case-d8775fb2aace
 */
@Configuration
@Slf4j
public class RedisStreamConfig {

    public static final String STREAM_KEY = "some.stream.name2";

    /**
     * For RedisStreamListener
     */
    @Bean
    StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamContainer(
            RedisConnectionFactory connectionFactory,
            RedisStreamListener streamListener
    ) {

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(1))
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
                StreamMessageListenerContainer.create(connectionFactory, options);

//        container.receive(StreamOffset.create(STREAM_KEY, ReadOffset.from("0-0")), streamListener);
        container.receive(StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()), streamListener);
        container.start();
        return container;
    }
}
