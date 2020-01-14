package com.techswitch.ispy.integration;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
public class SampleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    private Jdbi jdbi;

    @BeforeEach
    public void setUp() {
        jdbi = Jdbi.create(dataSource);
    }

    @AfterEach
    public void tearDown() {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM sample").execute());
    }

    @Test
    public void samplesShouldReturnAllSamples() throws Exception {
        addSample("Harry Potter");

        mockMvc.perform(get("/samples"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("[0].name").value("Harry Potter"));
    }

    private void addSample(String name) {
        jdbi.withHandle(handle -> handle.createUpdate(
                "INSERT INTO sample (id, name) VALUES (1, :name)")
                .bind("name", name)
                .execute()
        );
    }
}
