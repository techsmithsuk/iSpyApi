package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.models.request.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles("testDataSource")
public class ReportControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Jdbi jdbi;

    @Autowired
    private ReportService reportService;

    @Test
    public void validRequest_createReport_thenStatusIsCreated() throws Exception {
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
    public void invalidRequest_createReport_returnsValidationError() throws Exception {
        Report report = new Report(1L, "12-02-2019", "London", "");
        String requestJson = objectMapper.writeValueAsString(report);

        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("Description cannot be empty"));
    }

    @Test
    public void givenRequestWithInvalidJson_theStatusBadRequest() throws Exception {
        String requestJson = "{\"suspectId\":}";
        mockMvc.perform(post("http://localhost:8080/reports/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Error").value("Bad Request"));
    }


    @Test
    public void givenValidUrl_thenReturnListOfReports() throws Exception {
        addFakeData(5);
        mockMvc.perform(get("http://localhost:8080/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5)
                );
    }

    private void addFakeData(int numberOfEntries) {
        for (int i = 0; i < numberOfEntries; i++) {
            jdbi.withHandle(handle ->
                    handle.createUpdate("INSERT INTO reports (suspect_id, date_of_sighting, location, description, timestamp_submitted) " +
                            "VALUES (1, '11/02/1994'::date, 'Harlow' , 'he has a giant left hand', now()::timestamp) "
                    ).execute());
        }
    }
}