package com.techswitch.ispy.controllers;
import com.techswitch.ispy.config.IntegrationTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles("testDataSource")
public class InternalServerErrorTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void exceptionThrown_thenConvertsToCustom500() throws Exception {
        mockMvc.perform(get("http://localhost:8080/throwsException"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.Error").value("Internal Server Error"));
    }
}
