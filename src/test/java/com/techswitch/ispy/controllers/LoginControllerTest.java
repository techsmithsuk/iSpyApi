package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.models.LoginDetails;
import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

public class LoginControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Jdbi jdbi;

    @Test
    public void testValidUsernameAndPassword() throws Exception {

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername("username");
        loginDetails.setPassword("password");

        LoginController loginController = new LoginController("username","password");
        ResponseEntity responseEntity = loginController.validateLoginDetails(loginDetails);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(TokenValidator.validateToken(responseEntity.getBody().toString()));
    }

    @Test
    public void testInvalidUsernameAndPassword() throws Exception {

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername("fake");
        loginDetails.setPassword("fake");

        LoginController loginController = new LoginController("username","password");
        ResponseEntity responseEntity = loginController.validateLoginDetails(loginDetails);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
