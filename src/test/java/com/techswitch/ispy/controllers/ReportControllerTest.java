package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class ReportControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    public void givenValidRequest_thenStatusIsCreated() throws Exception {
        Report report = new Report(1L, "12-02-2019", "London", "description text");
        String requestJson = objectMapper.writeValueAsString(report);
        when(reportService.createReport(any(Report.class))).thenReturn(1L);

        mockMvc.perform(post("http://localhost:8080/report/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenRequestWithEmptyDescription_thenStatusBadRequest() throws Exception {
        Report report = new Report(1L, "12-02-2019", "London", "");
        String requestJson = objectMapper.writeValueAsString(report);


        mockMvc.perform(post("http://localhost:8080/report/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenRequestWithWrongDateFormat_thenStatusBadRequest() throws Exception {
        Report report = new Report(1L, "12022019", "London", "description");
        String requestJson = objectMapper.writeValueAsString(report);


        mockMvc.perform(post("http://localhost:8080/report/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenRequestWithEmptyDate_thenStatusIsCreated() throws Exception {
        Report report = new Report(1L, "", "London", "description");
        String requestJson = objectMapper.writeValueAsString(report);
        when(reportService.createReport(any(Report.class))).thenReturn(1L);

        mockMvc.perform(post("http://localhost:8080/report/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenValidRequest_whenIssueWithDatabase_thenStatusInternalServerError() throws Exception {
        Report report = new Report(1L, "12-02-2019", "London", "description");
        String requestJson = objectMapper.writeValueAsString(report);
        when(reportService.createReport(any(Report.class))).thenReturn(0L);

        mockMvc.perform(post("http://localhost:8080/report/create")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

}
