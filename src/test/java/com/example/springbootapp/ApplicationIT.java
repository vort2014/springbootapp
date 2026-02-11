package com.example.springbootapp;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@Slf4j
public abstract class ApplicationIT {

    private static final int RABBITMQ_PORT = 5672;
    // TODO: make the same container as in compose.yml
    static GenericContainer rabbitmq = new GenericContainer("rabbitmq:latest")
            .withExposedPorts(RABBITMQ_PORT);

    @LocalServerPort
    private Integer port;

    static {
        rabbitmq.start();
        log.info("Started RabbitMQ Server on port {}", rabbitmq.getMappedPort(RABBITMQ_PORT));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            rabbitmq.stop();
            log.info("Stopped RabbitMQ Server");
        }));
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.port", () -> rabbitmq.getMappedPort(RABBITMQ_PORT));
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // enableLoggingOfRequestAndResponse for all tests
    }

    /**
     * Clear all data in the database.
     * Additionally, it checks correctness of JPA annotation mappings
     */
    @AfterEach
    void afterEach(@Autowired List<JpaRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
