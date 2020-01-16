package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.models.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles("testDataSource")
public class ReportControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidRequest_thenStatusIsCreated() throws Exception {
        Report report = new Report(1L, "12-02-2019", "London", "description text");
        String requestJson = objectMapper.writeValueAsString(report);

        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.suspectId").value("1"))
                .andExpect(jsonPath("$.dateOfSighting").value("12-02-2019"))
                .andExpect(jsonPath("$.location").value("London"))
                .andExpect(jsonPath("$.description").value("description text"))
                .andExpect(jsonPath("$.timestampSubmitted").exists())
                .andReturn();
    }

    @Test
    public void givenRequestWithEmptyDescription_thenStatusBadRequest() throws Exception {
        Report report = new Report(1L, "12-02-2019", "London", "");
        String requestJson = objectMapper.writeValueAsString(report);

        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("Description cannot be empty"));
    }

    @Test
    public void givenRequestWithWrongDateFormat_thenStatusBadRequest() throws Exception {
        Report report = new Report(1L, "12022019", "London", "description");
        String requestJson = objectMapper.writeValueAsString(report);

        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("Please use a DD-MM-YYYY format for date"));
    }

    @Test
    public void givenRequestWithEmptyDate_thenStatusBadRequest() throws Exception {
        Report report = new Report(1L, "", "London", "description");
        String requestJson = objectMapper.writeValueAsString(report);
        System.err.println(requestJson);
        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("Please use a DD-MM-YYYY format for date"));
    }

    @Test
    public void givenRequestWithEmptySuspectId_thenStatusBadRequest() throws Exception {
        String requestJson ="{\"suspectId\":\"\",\"date\":\"12-02-2019\",\"location\":\"London\",\"description\":\"description\"}";
        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.suspectId").value("Suspect ID cannot be null"));
    }

    @Test
    public void givenRequestWithInvalidJson_thenInternalServerError() throws Exception {
        String requestJson ="{\"suspectId\":}";
        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Error").value("Bad Request"));
    }
}
