package com.techswitch.ispy.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(System.getenv("DATABASE_URL"))
                .build();
    }

}
