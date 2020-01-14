package com.techswitch.ispy.integration;

import com.techswitch.ispy.config.AppConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    @Primary
    public DataSource getTestDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:ispy")
                .build();
    }
}
