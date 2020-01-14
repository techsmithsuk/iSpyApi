package com.techswitch.ispy.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.io.IOException;

@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    @Primary
    public DataSource getTestDataSource() throws IOException {
        EmbeddedPostgres pg = EmbeddedPostgres.start();
        return pg.getPostgresDatabase();
    }

}
