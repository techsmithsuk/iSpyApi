package com.techswitch.ispy.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.services.FbiDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles("testDataSource")
public class FbiDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FbiDataService fbiDataService;

    @Test
    public void fetchFbiData_givenAValidJson_thenReturnStatusOk() throws Exception {

        when(fbiDataService.saveDataToDatabase(any(JsonNode.class))).thenReturn(1);

        mockMvc.perform(get("http://localhost:8080/admin/fetch-fbi-data"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.Success").value("1 row(s) has been added to database."));
    }

}
