package com.techswitch.ispy.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;


@TestConfiguration
public class IntegrationTestConfig{

    @Bean
    @Profile("testDataSource")
    public DataSource getDataSource() throws IOException {
        EmbeddedPostgres pg = EmbeddedPostgres.builder().start();
        return pg.getPostgresDatabase();
    }


    @Bean(name="signer")
    @Profile("testSigner")
    public String getSigner(){
        return "signerstring";
    }

    @Bean
    @Profile("testLoginConfig")
    public LoginConfig getLoginConfig() {
        return new LoginConfig(
                "username",
                "password"
        );
    }

    @RestController
    public static class TestController {

        @RequestMapping("/throwsException")
        public void throwsException() throws Exception {
            throw new Exception();
        }
    }
}
