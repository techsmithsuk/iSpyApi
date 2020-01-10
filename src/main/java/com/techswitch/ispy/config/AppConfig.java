package com.techswitch.ispy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name="databaseUrl")
    public String getDatabaseUrl(){
        return System.getenv("DATABASE_URL");
    }

}
