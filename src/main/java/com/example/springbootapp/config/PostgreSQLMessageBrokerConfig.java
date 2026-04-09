package com.example.springbootapp.config;

import com.zaxxer.hikari.util.DriverDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Properties;

/**
 * Channel names are unique within a database
 * Clients require no special grants to use LISTEN/NOTIFY
 * When NOTIFY is used within a transaction, clients receive the notification only if it completes successfully
 * Also, clients will receive a single notification if multiple NOTIFY commands are sent to the same channel using the same payload within a transaction.
 * <p>
 * Channels act like topics.
 */
@Configuration
@Slf4j
public class PostgreSQLMessageBrokerConfig {

    public static final String ORDERS_CHANNEL = "orders";
    public static final String SPECIAL_JDBC_TEMPLATE = "SPECIAL_JDBC_TEMPLATE";

    /**
     * We create separate DataSource from Hikari pool
     * to use by our special JdbcTemplate for PostgreSQL notification logic.
     * This prevents consuming the main JDBC pool.
     */
    @Bean(SPECIAL_JDBC_TEMPLATE)
    JdbcTemplate specialJdbcTemplate(DataSourceProperties props) {
        DriverDataSource ds = new DriverDataSource(
                props.determineUrl(),
                props.determineDriverClassName(),
                new Properties(),
                props.determineUsername(),
                props.determinePassword());
        return new JdbcTemplate(ds);
    }
}
