package com.example.springbootapp.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * https://docs.spring.io/spring-boot/reference/features/task-execution-and-scheduling.html
 * https://docs.spring.io/spring-framework/reference/integration/scheduling.html
 */
@Configuration
@EnableScheduling
@EnableAsync
@Slf4j
public class Context {

    /**
     * We need this bean to define Exception handler for @Async methods
     */
    @Bean
    AsyncConfigurer asyncConfigurer() {
        return new AsyncConfigurer() {

            @Override
            public @Nullable AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return (ex, method, params) -> {
                    log.error("Exception caught in AsyncUncaughtExceptionHandler.getAsyncUncaughtExceptionHandler():", ex);
                };
            }
        };
    }
}
