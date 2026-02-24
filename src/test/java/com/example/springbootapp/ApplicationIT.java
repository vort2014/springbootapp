package com.example.springbootapp;

import com.example.springbootapp.activemq.ActiveMQProducer;
import com.example.springbootapp.config.TestMessageListener;
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
import org.testcontainers.containers.GenericContainer;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@Slf4j
public abstract class ApplicationIT {

    @LocalServerPort
    private Integer port;
    @Autowired
    protected TestMessageListener testMessageListener;
    @Autowired
    protected ActiveMQProducer activeMQProducer;

    // start ActiveMQ testcontainer
    static {
        var exposePort = 61616;
        var activeMQ = new GenericContainer("apache/activemq:5.19.2")
                .withExposedPorts(exposePort);
        activeMQ.start();
        var activeMQPort = activeMQ.getMappedPort(exposePort);
        System.setProperty("ACTIVE_MQ_PORT", activeMQPort.toString());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Stopping ActiveMQ container...");
            activeMQ.stop();
            log.info("ActiveMQ container stopped.");
        }));
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
        testMessageListener.clear();
    }
}
