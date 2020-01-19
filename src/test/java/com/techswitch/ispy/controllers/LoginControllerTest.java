package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.models.LoginDetails;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource","testSigner","testLoginConfig"})
public class LoginControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Jdbi jdbi;

    @Test
    public void giveValidLoginDetails_thenReturnsToken() throws Exception {
        mockMvc.perform(post("http://localhost:8080/login")
                .param("username","username")
                .param("password","password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
    }

    @Test
    public void giveInvalidLoginDetails_theStatusBadRequest() throws Exception {
        mockMvc.perform(post("http://localhost:8080/login")
                .param("username","wrongUsername")
                .param("password","wrongPassword"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
