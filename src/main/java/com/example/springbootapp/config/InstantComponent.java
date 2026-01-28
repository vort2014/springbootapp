package com.example.springbootapp.config;

import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Jackson returns Instant with 9 millis by default e.g. 2025-03-28T15:28:32.400680500Z
 * We need only 3 millis in response so this component redefines Instant Serializer.
 * <p>
 * https://docs.spring.io/spring-boot/reference/features/json.html
 */
@JacksonComponent
public class InstantComponent {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX") // 2025-03-28T15:04:22.247Z
            .withZone(ZoneOffset.UTC);

    public static class Serializer extends ValueSerializer<Instant> {
        @Override
        public void serialize(Instant value, JsonGenerator jgen, SerializationContext context) {
            jgen.writeString(DATE_TIME_FORMATTER.format(value));
        }
    }

//  class Deserializer extends ValueDeserializer<MyObject> {
}
