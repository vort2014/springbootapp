package com.example.springbootapp.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * https://docs.spring.io/spring-boot/reference/features/task-execution-and-scheduling.html
 * https://docs.spring.io/spring-framework/reference/integration/scheduling.html
 *
 */
@Configuration
@Slf4j
public class SpringEventConfig {

    /**
     * Creates AsyncTaskExecutor with default values for @Async annotation.
     * If async event listener blocks, it doesn't block other threads.
     *
     * Better set this property instead of creating this Bean:
     * spring.threads.virtual.enabled=true
     */
    @Bean
    SimpleAsyncTaskExecutor taskExecutor(SimpleAsyncTaskExecutorBuilder builder) {
        return builder.build();
    }

    /**
     * We need this bean to define Exception handler for @Async methods
     */
    @Bean
    AsyncConfigurer asyncConfigurer() {
        return new AsyncConfigurer() {

            @Override
            public @Nullable AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return (ex, method, params) -> {
                    log.info("Exception caught in SpringEventListener method:", ex);
                };
            }
        };
    }
}
