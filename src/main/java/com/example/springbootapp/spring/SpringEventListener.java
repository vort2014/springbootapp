package com.example.springbootapp.spiring;

import com.example.springbootapp.web.controller.company.CompanyResponseJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringEventListener {

    @Async
    @EventListener
    void onMessage1(CompanyResponseJson message) {
        log.info("onMessage1 message: {}", message);
    }

    /**
     * This listener shouldn't block other listeners as it executes in a separate thread
     * as we defined in the configuration SpringEventConfig
     */
    @Async
    @EventListener
    void onMessage2(CompanyResponseJson message) throws InterruptedException {
        TimeUnit.DAYS.sleep(1);
    }

    /**
     * This method is called based on condition.
     * It can't also throw an exception which doesn't affect other listeners
     */
    @Async
    @EventListener(condition = "#message.name == null")
    void onMessage3(CompanyResponseJson message) {
        throw new RuntimeException("onMessage3 throws exception");
//        log.info("onMessage3 message: {}", message);
    }
}
